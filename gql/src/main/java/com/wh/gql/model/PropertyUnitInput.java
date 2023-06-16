package com.wh.gql.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.lang.reflect.Field;

@Data
@Builder
@Jacksonized
public class PropertyUnitInput {

    /**
     * GQL:
     * -- PropertyUnitTypesInput --
     * unitCode: String -
     * add operation:  MUST be nonNull & NOT match existing property unit code
     * edit operation: MUST be nonNull & match existing property unit code
     * unitType: String     --      e.g. room, villa, apartment etc.
     * add operation:  MUST be nonNull
     * name: String
     * add operation:  MUST be nonNull
     * # Matches unit types with minimumPersons >= input
     * minimumPersons: Int
     * add operation:  MUST be nonNull
     * # Matches unit types with maximumPersons <= input
     * maximumPersons: Int
     * add operation:  MUST be nonNull
     * Invalid PAX operation NEEDS VALIDATION
     */

    private String unitCode;

    private String unitType;

    private String name;

    private Integer minimumPersons;

    private Integer maximumPersons;

    /**
     * https://stackoverflow.com/questions/38771556/is-it-bad-to-throw-more-than-one-exception-in-a-method-signature
     * @return
     * @throws IllegalAccessException
     */
    private boolean hasAtLeastOneEmpty() throws IllegalAccessException {
        Class clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.get(this) == null) {
                return true;
            }
        }
        return false;
    }

    public boolean validInputForPropertyUnitForAddOperation() throws IllegalAccessException {
        return !hasAtLeastOneEmpty();
    }

    /**
     * For EDIT operation to make sense a valid unitCode is required as operation key AND
     * a valid - nonNull - field
     * e.g. for unitCode: ROOM_AA want to change maximumPersons from 2 (previous value) to 3 (current value)
     * @return
     * @throws IllegalAccessException
     */
    public boolean validInputForPropertyUnitForEditOperation() throws IllegalAccessException {
        boolean unitCodeEmptyOrBlankOrNull = unitCode == null || unitCode.isEmpty() || unitCode.isBlank();

        int i = 0;
        Class clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.get(this) != null) {
                i++;
            }
        }

        boolean hasTwoOrMoreFieldsNotNull = i > 1;

        return !unitCodeEmptyOrBlankOrNull && hasTwoOrMoreFieldsNotNull;
    }


}
