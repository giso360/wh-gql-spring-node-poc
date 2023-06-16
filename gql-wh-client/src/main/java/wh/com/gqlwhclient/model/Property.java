package wh.com.gqlwhclient.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class Property {

    private String code;
    private String name;
    private String type;
    private int rating;
    private List<PropertyUnit> unitTypes;
}
