package wh.com.gqlwhclient.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
public class PropertyUnit {

    public static final Predicate<PropertyUnit> INVALID_RESPONSE = pUnit -> pUnit.getMinimumPersons() == -1;

    private String id;
    private String code;
    private String name;
    private String unitType;
    private int minimumPersons;
    private int maximumPersons;
}
