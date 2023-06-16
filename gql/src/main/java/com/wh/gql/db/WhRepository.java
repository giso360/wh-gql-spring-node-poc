package com.wh.gql.db;

import com.wh.gql.model.Property;
import com.wh.gql.model.PropertyUnit;
import com.wh.gql.model.PropertyUnitInput;
import com.wh.gql.model.ReportingObject;

import java.util.List;
import java.util.Optional;

public interface WhRepository {

    //    properties: [Property]
    List<Property> getAllProperties();

    //    propertyByCode(propertyCode: String!): Property!
    Optional<Property> findPropertyByCode(String propertyCode);

    Optional<PropertyUnit> addPropertyUnitForProperty(PropertyUnitInput unitInput, String propertyCode) throws IllegalAccessException;

    List<PropertyUnit> getAllPropertyUnitsInProperty(Property property);


    void savePropertyUnit(PropertyUnit propertyUnit, String propertyCode);

    void deletePropertyUnit(PropertyUnit propertyUnit, String propertyCode);

    void deletePropertyUnitByUnitCodeAndPropertyCode(String propertyUnitCode, String propertyCode);

    Optional<PropertyUnit> editPropertyUnitForProperty(PropertyUnitInput propertyUnitInput, String propertyCode) throws IllegalAccessException;

    Optional<PropertyUnit> findPropertyUnitByPropertyCodeAndUnitCode(String propertyUnitCode, String propertyCode);

    ReportingObject getAllWhPropertyUnits();

}
