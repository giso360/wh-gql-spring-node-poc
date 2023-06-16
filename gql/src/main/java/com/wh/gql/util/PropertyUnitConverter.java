package com.wh.gql.util;


import com.wh.gql.model.PropertyUnit;
import com.wh.gql.model.PropertyUnitInput;
import org.apache.tomcat.websocket.WsRemoteEndpointImplClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.UUID;


public class PropertyUnitConverter {

    public static final PropertyUnitInput PROPERTY_UNIT_INPUT_TEST =
            PropertyUnitInput.
            builder().
            unitCode("001").
            unitType("TYPE01").
            name("NAME01").
            minimumPersons(2).
            maximumPersons(2).
            build();

    /**
     * ADD action converter
     * @param propertyUnitInput
     * @return
     */
    public static PropertyUnit convert(PropertyUnitInput propertyUnitInput) {
        return PropertyUnit.
                builder().
                id(UUID.randomUUID()).
                code(propertyUnitInput.getUnitCode()).
                name(propertyUnitInput.getName()).
                unitType(propertyUnitInput.getUnitType()).
                minimumPersons(propertyUnitInput.getMinimumPersons()).
                maximumPersons(propertyUnitInput.getMaximumPersons()).
                build();
    }

    public static PropertyUnit convert(PropertyUnitInput propertyUnitInput, PropertyUnit oldPropertyUnit) {

        String name = propertyUnitInput.getName() == null ||
                propertyUnitInput.getName().isEmpty() ||
                propertyUnitInput.getName().isBlank() ? oldPropertyUnit.getName(): propertyUnitInput.getName();

        String unitType = propertyUnitInput.getUnitType() == null ||
                propertyUnitInput.getUnitType().isEmpty() ||
                propertyUnitInput.getUnitType().isBlank() ? oldPropertyUnit.getUnitType(): propertyUnitInput.getUnitType();

        int minimumPersons = propertyUnitInput.getMinimumPersons() == null ?
                oldPropertyUnit.getMinimumPersons(): propertyUnitInput.getMinimumPersons();

        int maximumPersons = propertyUnitInput.getMaximumPersons() == null ?
                oldPropertyUnit.getMaximumPersons(): propertyUnitInput.getMaximumPersons();

        return PropertyUnit.
                builder().
                id(oldPropertyUnit.getId()).
                code(propertyUnitInput.getUnitCode()).
                name(name).
                unitType(unitType).
                minimumPersons(minimumPersons).
                maximumPersons(maximumPersons).
                build();
    }

}
