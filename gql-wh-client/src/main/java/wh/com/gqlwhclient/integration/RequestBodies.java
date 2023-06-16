package wh.com.gqlwhclient.integration;

import wh.com.gqlwhclient.model.PropertyUnitInput;

import java.util.Map;

public class RequestBodies {

    //language=GraphQl
    public static final String PROPERTY_BY_CODE_REQ_BODY = """
                 query($propertyCode: String!) {
                                       propertyByCode(propertyCode: $propertyCode){
                                             code
                                             name
                                             type
                                             rating
                                             unitTypes {
                                               id
                                               code
                                               name
                                               unitType
                                               minimumPersons
                                               maximumPersons
                                             }
                                       }
                                     }
            """;

    //language=GraphQl
    public static final String ADD_PROPERTY_UNIT_REQ_BODY = """

                mutation ($propertyUnitInput: PropertyUnitTypesInput, $propertyCode: String!) {
                    addPropertyUnitToProperty(propertyUnitInput: $propertyUnitInput, propertyCode: $propertyCode){
                                  id
                                  code
                                  name
                                  unitType
                                  minimumPersons
                                  maximumPersons
                    }
                }
                        
            """;

    //language=GraphQl
    public static final String DEL_PROPERTY_UNIT_REQ_BODY = """

                mutation($propertyUnitCode: String!, $propertyCode: String!){
                  deletePropertyUnitOfProperty(propertyUnitCode: $propertyUnitCode, propertyCode: $propertyCode)\s
                }
            """;

    public static final PropertyUnitInput ADD_INPUT_TEST = PropertyUnitInput.
            builder().
            unitCode("VILLA_TEST").
            unitType("villa").
            name("villa test").
            minimumPersons(3).
            maximumPersons(5).
            build();

    public static final Map<String, Object> DEL_INPUT_TEST = Map.of("propertyUnitCode", (Object) "VILLA_A",
            "propertyCode", (Object) "HILTON_A");
}
