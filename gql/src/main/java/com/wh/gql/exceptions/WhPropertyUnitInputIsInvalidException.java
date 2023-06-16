package com.wh.gql.exceptions;

import com.wh.gql.enums.EWhAction;

public class WhPropertyUnitInputIsInvalidException extends WhGenericGQLException {

    private static String errorMessageBuilder(EWhAction action) {
        StringBuilder msgBuilder = new StringBuilder();
        switch (action) {
            case ADD ->
                    msgBuilder.append("PropertyUnit ADD operation - All fields of PropertyUnitInput request object must " +
                            "be filled !!!");
            case EDIT ->
                    msgBuilder.append("PropertyUnit EDIT operation - unitCode field of PropertyUnitInput must not be " +
                            "NULL and request object must have at least one extra nonNull field!!!");
            case DEL ->
                    msgBuilder.append("Either the property OR the unit property codes provided is NULL/BLANK !!!");
        }
        return msgBuilder.toString();
    }

    public WhPropertyUnitInputIsInvalidException(EWhAction action) {
        super(errorMessageBuilder(action));
    }

}
