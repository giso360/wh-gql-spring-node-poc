package wh.com.gqlwhclient.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class AddPropertyUnitOutput {
    private Map<String, PropertyUnit> addPropertyUnitToProperty;
}
