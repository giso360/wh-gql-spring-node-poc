package wh.com.gqlwhclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import wh.com.gqlwhclient.config.ClientConfiguration;
import wh.com.gqlwhclient.integration.EWhActions;
import wh.com.gqlwhclient.integration.RequestBodies;
import wh.com.gqlwhclient.model.PropertyUnit;
import wh.com.gqlwhclient.model.PropertyUnitInput;
import wh.com.gqlwhclient.util.WhGqlInputGenerator;

import java.util.*;

@Slf4j
public class TestRunner implements CommandLineRunner {

    private final List<String> STORED_UNIT_CODES = new ArrayList<>();

    // TODO: Pass/Get GQL URL and PropertyCode from application.properties
    private final ClientConfiguration clientConfiguration = new ClientConfiguration();

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/wh").build();

    private final HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder(webClient).build();

    private static final Random RANDOM = new Random();

    public TestRunner() {
    }

    @Override
    public void run(String... args) throws Exception {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                EWhActions selectedAction = selectOp();
                log.info("SELECTED OP: {}", selectedAction.toString());
                switch (selectedAction){
                    case ADD -> doAddPropertyUnit();
                    case DEL -> doDeletePropertyUnit();
                }
            }
        }, 0, 2000);
    }

    private void doDeletePropertyUnit() {
        System.out.println("========================");
        System.out.println("DELETING PROPERTY UNIT ...");
        System.out.println("STORED UNITS BEFORE: ");
        System.out.println(STORED_UNIT_CODES);
        deletePropertyUnit();
        System.out.println("STORED UNITS AFTER: ");
        System.out.println(STORED_UNIT_CODES);
        System.out.println("========================");
    }

    private void deletePropertyUnit() {
        // Check id STORED_UNIT_CODES is not empty
        if (STORED_UNIT_CODES.isEmpty()){
            return;
        }
        // If STORED_UNIT_CODES contains element select random unit code
        String selectedUnitCode = STORED_UNIT_CODES.get(RANDOM.nextInt(STORED_UNIT_CODES.size()));
        // perform GQL call
        Mono<ClientGraphQlResponse> executed = httpGraphQlClient.
                document(RequestBodies.DEL_PROPERTY_UNIT_REQ_BODY).
                variables(Map.of("propertyUnitCode", selectedUnitCode,"propertyCode", (Object) "HILTON_B")).
                execute();
        ClientGraphQlResponse response = executed.block();
        System.out.println(response.toString());
        System.out.println();
        // If response is not error -> remove unitCode element from STORED_UNIT_CODES
        if (response.toString().contains("deletePropertyUnitOfProperty=SUCCESS")){
            STORED_UNIT_CODES.remove(selectedUnitCode);
        }
    }

    private void doAddPropertyUnit(){
        System.out.println("========================");
        System.out.println("ADDING PROPERTY UNIT ...");
        System.out.println("STORED UNITS BEFORE: ");
        System.out.println(STORED_UNIT_CODES);
        addPropertyUnit();
        System.out.println("STORED UNITS AFTER: ");
        System.out.println(STORED_UNIT_CODES);
        System.out.println("========================");
    }

    private EWhActions selectOp() {
        return EWhActions.randomAction();
    }

    private PropertyUnit addPropertyUnit() {
        // 1. prepare POST request body
        PropertyUnitInput propertyUnitInput = WhGqlInputGenerator.getRandomPropertyUnitInput();
        // Check if UNIT CODE EXISTS in STORED_UNIT_CODES
        if (STORED_UNIT_CODES.contains(propertyUnitInput.getUnitCode())) {
            return null;
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("propertyUnitInput", propertyUnitInput);
        vars.put("propertyCode", "HILTON_B");
        // 2. perform GQL call and get response
        Mono<ClientGraphQlResponse> executedAdd = httpGraphQlClient.document(RequestBodies.ADD_PROPERTY_UNIT_REQ_BODY).
                variables(vars).execute();
        // 3. convert response to PropertyUnit -> propertyUnit
        PropertyUnit propertyUnitResponse = executedAdd.block().
                field("addPropertyUnitToProperty").toEntity(PropertyUnit.class);
        // 4. check if propertyUnit is error - i.e. minimumPersons == -1
        // if !error getCode from propertyUnit and add to STORED_UNIT_CODES
        if (!PropertyUnit.INVALID_RESPONSE.test(propertyUnitResponse)) {
            STORED_UNIT_CODES.add(propertyUnitResponse.getCode());
        } else {
            System.out.println("Addition failed");
        }
        return propertyUnitResponse;
    }


}
