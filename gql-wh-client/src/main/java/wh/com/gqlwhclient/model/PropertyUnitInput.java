package wh.com.gqlwhclient.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropertyUnitInput {

    private String unitCode;
    private String unitType;
    private String name;
    private int minimumPersons;
    private int maximumPersons;

}
