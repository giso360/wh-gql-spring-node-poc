package com.wh.gql.exceptions;

import lombok.NonNull;

public class WhPropertyUnitInputPAXException extends WhGenericGQLException {

    public WhPropertyUnitInputPAXException(String message) {
        super(message);
    }

    public WhPropertyUnitInputPAXException(@NonNull int minimumPersons, @NonNull int maximumPersons) {
        super(String.format("PAX VIOLATION - %d >= %d: Minimum Persons equal OF greater than Maximum Persons",
                minimumPersons, maximumPersons));
    }


}
