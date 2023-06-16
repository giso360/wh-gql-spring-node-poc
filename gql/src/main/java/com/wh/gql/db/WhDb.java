package com.wh.gql.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wh.gql.enums.EWhAction;
import com.wh.gql.exceptions.*;
import com.wh.gql.model.Property;
import com.wh.gql.model.PropertyUnit;
import com.wh.gql.model.PropertyUnitInput;
import com.wh.gql.model.ReportingObject;
import com.wh.gql.util.PropertyUnitConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

@Slf4j
@Component
public class WhDb implements WhRepository {

    private static final Predicate<String> ILLEGAL_STRING_INPUT = s -> s == null || s.isBlank() || s.isEmpty();

    private static final Predicate<PropertyUnit> ILLEGAL_PAX_PREDICATE = propertyUnit ->
            propertyUnit.getMinimumPersons() > propertyUnit.getMaximumPersons() || propertyUnit.getMaximumPersons() < 0;

    private List<Property> whProperties;

    private static final ClassPathResource WH_DB_RESOURCE = new ClassPathResource("wh_data.json");

    private static WhDb WHDB_INSTANCE;

    private WhDb() throws IOException {
        populateDb();
    }

    public synchronized static WhDb getInstance() throws IOException {
        if (WHDB_INSTANCE == null) {
            WHDB_INSTANCE = new WhDb();
        }
        return WHDB_INSTANCE;
    }

    public List<Property> getWhProperties() {
        return whProperties;
    }

    public void setWhProperties(List<Property> whProperties) {
        this.whProperties = whProperties;
    }

    private void populateDb() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Property[] properties = objectMapper.readValue(WH_DB_RESOURCE.getFile(), Property[].class);
        this.whProperties = new ArrayList<>(List.of(properties));
    }

    private boolean propertyCodeExists(String propertyCode) {
        return findPropertyByCode(propertyCode).isPresent();
    }

    private boolean propertyUnitExists(String propertyCode, String propertyUnitCode) {
        return whProperties.stream().filter(property -> property.getCode().equals(propertyCode)).findFirst().get().getPropertyUnits().stream().anyMatch(propertyUnit -> propertyUnit.getCode().equals(propertyUnitCode));
    }

    @Override
    public Optional<Property> findPropertyByCode(String propertyCode) {
        return Optional.of(whProperties.stream().filter(property -> property.getCode().equals(propertyCode)).
                findFirst().orElseThrow(() -> new WhPropertyNotFoundException(propertyCode)));
    }

    @Override
    public List<Property> getAllProperties() {
        return whProperties.isEmpty() ? new ArrayList<>() : whProperties;
    }

    @Override
    public List<PropertyUnit> getAllPropertyUnitsInProperty(Property property) {
        return whProperties.stream().filter(p -> p.getCode().equals(property.getCode())).findFirst().orElse(Property.ERROR_PROPERTY).getPropertyUnits();
    }

    @Override
    public void savePropertyUnit(PropertyUnit propertyUnit, String propertyCode) {
        if (ILLEGAL_PAX_PREDICATE.test(propertyUnit)) {
            log.info("PAX VIOLATION - {} >= {}: Minimum Persons equal OF greater than Maximum Persons",
                    propertyUnit.getMinimumPersons(), propertyUnit.getMaximumPersons());
            throw new WhPropertyUnitInputPAXException(propertyUnit.getMinimumPersons(), propertyUnit.getMaximumPersons());
        }
        for (Property property : whProperties) {
            if (property.getCode().equals(propertyCode)) {
                property.getPropertyUnits().add(propertyUnit);
                break;
            }
        }
    }

    @Override
    public void deletePropertyUnit(PropertyUnit propertyUnit, String propertyCode) {
        for (Property property : whProperties) {
            if (property.getCode().equals(propertyCode)) {
                Iterator<PropertyUnit> propertyUnitIterator = property.getPropertyUnits().iterator();
                while (propertyUnitIterator.hasNext()) {
                    if (propertyUnitIterator.next().getCode().equals(propertyUnit.getCode())) {
                        propertyUnitIterator.remove();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Optional<PropertyUnit> addPropertyUnitForProperty(PropertyUnitInput unitInput, String propertyCode) throws IllegalAccessException {

        if (!unitInput.validInputForPropertyUnitForAddOperation()) {
            throw new WhPropertyUnitInputIsInvalidException(EWhAction.ADD);
        }

        if (!propertyCodeExists(propertyCode)) {
            throw new WhPropertyNotFoundException(propertyCode);
        }

        if (propertyUnitExists(propertyCode, unitInput.getUnitCode())) {
            throw new WhPropertyUnitAlreadyExistsException(propertyCode, unitInput.getUnitCode());
        }

        PropertyUnit newPropertyUnit = PropertyUnitConverter.convert(unitInput);
        savePropertyUnit(newPropertyUnit, propertyCode);
        return Optional.of(newPropertyUnit);
    }

    @Override
    public Optional<PropertyUnit> editPropertyUnitForProperty(PropertyUnitInput propertyUnitInput, String propertyCode)
            throws IllegalAccessException {

        if (!propertyCodeExists(propertyCode)) {
            throw new WhPropertyNotFoundException(propertyCode);
        }

        if (!propertyUnitInput.validInputForPropertyUnitForEditOperation()) {
            throw new WhPropertyUnitInputIsInvalidException(EWhAction.EDIT);
        }

        if (!propertyUnitExists(propertyCode, propertyUnitInput.getUnitCode())) {
            throw new WhPropertyUnitDoesNotExistException(propertyCode, propertyUnitInput.getUnitCode());
        }

        PropertyUnit oldPropertyUnit = findPropertyUnitByPropertyCodeAndUnitCode(propertyUnitInput.getUnitCode(), propertyCode).get();
        PropertyUnit newPropertyUnit = PropertyUnitConverter.convert(propertyUnitInput, oldPropertyUnit);

        if (ILLEGAL_PAX_PREDICATE.test(newPropertyUnit)){
            throw new WhPropertyUnitInputPAXException(newPropertyUnit.getMinimumPersons(),
                    newPropertyUnit.getMaximumPersons());
        }

        deletePropertyUnit(oldPropertyUnit, propertyCode);
        savePropertyUnit(newPropertyUnit, propertyCode);

        return Optional.of(newPropertyUnit);
    }

    @Override
    public void deletePropertyUnitByUnitCodeAndPropertyCode(String propertyUnitCode, String propertyCode) {
        if (ILLEGAL_STRING_INPUT.test(propertyUnitCode) || ILLEGAL_STRING_INPUT.test(propertyCode)){
            throw new WhPropertyUnitInputIsInvalidException(EWhAction.DEL);
        }

        if (!propertyCodeExists(propertyCode)) {
            throw new WhPropertyNotFoundException(propertyCode);
        }

        if (!propertyUnitExists(propertyCode, propertyUnitCode)){
            throw new WhPropertyUnitDoesNotExistException(propertyCode, propertyUnitCode);
        }

        Optional<PropertyUnit> propertyUnitToBeDeleted = findPropertyUnitByPropertyCodeAndUnitCode(propertyUnitCode,
                propertyCode);

        deletePropertyUnit(propertyUnitToBeDeleted.orElse(null), propertyCode);
    }

    @Override
    public Optional<PropertyUnit> findPropertyUnitByPropertyCodeAndUnitCode(String propertyUnitCode,
                                                                            String propertyCode) {
        return whProperties.stream().filter(property -> property.getCode().equals(propertyCode)).findFirst().get().
                getPropertyUnits().stream().filter(propertyUnit ->
                        propertyUnit.getCode().equals(propertyUnitCode)).findFirst();
    }

    @Override
    public ReportingObject getAllWhPropertyUnits() {
        int result = 0;
        for (Property property: getWhProperties()) {
            result += property.getPropertyUnits().size();
        }

        return new ReportingObject(result);
    }
}
