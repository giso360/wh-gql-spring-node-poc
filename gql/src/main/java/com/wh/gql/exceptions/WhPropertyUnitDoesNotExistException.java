package com.wh.gql.exceptions;

public class WhPropertyUnitDoesNotExistException extends WhGenericGQLException {
    public WhPropertyUnitDoesNotExistException(String propertyCode, String propertyUnitCode) {
        super(String.format("PropertyUnit with code [ %s ] for property [ %s ] DOES NOT exist and therefore CANNOT be EDITED !!!", propertyUnitCode, propertyCode));
    }
}
