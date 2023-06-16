package com.wh.gql.exceptions;

/**
 * Used for ADD operation.
 * Need to ADD a new property unit => Check first that it DOES NOT already exist
 */
public class WhPropertyUnitAlreadyExistsException extends WhGenericGQLException {

    public WhPropertyUnitAlreadyExistsException(String propertyCode, String propertyUnitCode) {
        super(String.format("PropertyUnit with code [ %s ] for property [ %s ] already exists !!!", propertyUnitCode, propertyCode));
    }

}
