package wh.com.gqlwhclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import wh.com.gqlwhclient.config.ClientConfiguration;
import wh.com.gqlwhclient.integration.RequestBodies;
import wh.com.gqlwhclient.model.Property;
import wh.com.gqlwhclient.model.PropertyUnit;
import wh.com.gqlwhclient.model.PropertyUnitInput;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class ClientController {

    private final HttpGraphQlClient httpGraphQlClient;

    private final ClientConfiguration clientConfiguration;

    public ClientController(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
        WebClient client = WebClient.builder().baseUrl(clientConfiguration.getUrl()).build();
        httpGraphQlClient = HttpGraphQlClient.builder(client).build();
    }

    private Mono<Property> getPropertyByCodeMethod(String pCode) {
        return httpGraphQlClient.
                document(RequestBodies.PROPERTY_BY_CODE_REQ_BODY).
                variable("propertyCode", pCode).
                retrieve("propertyByCode").
                toEntity(Property.class);
    }

    private Mono<ClientGraphQlResponse> addPropertyUnit(PropertyUnitInput input) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("propertyUnitInput", input);
        vars.put("propertyCode", clientConfiguration.getProperty());
        System.out.println(vars.get("propertyCode"));
        return httpGraphQlClient.
                document(RequestBodies.ADD_PROPERTY_UNIT_REQ_BODY).
                variables(vars).execute();
    }

    @GetMapping("/{pCode}")
    public Property getPropertyByCode(@PathVariable String pCode) {
        return getPropertyByCodeMethod(pCode).block();
    }

    @GetMapping("/add")
    public PropertyUnit addPropertyUnit() {
        ClientGraphQlResponse response = addPropertyUnit(RequestBodies.ADD_INPUT_TEST).block();
        return response.toEntity(PropertyUnit.class);
    }

//    Timer t = new Timer();
//    t.schedule(new
//    TimerTask() {
//        @Override
//        public void run () {
//            System.out.println("Hello World");
//        }
//    },0,5000);

}
