package com.wh.gql.controller;

import com.wh.gql.db.WhDb;
import com.wh.gql.exceptions.*;
import com.wh.gql.model.Property;
import com.wh.gql.model.PropertyUnit;
import com.wh.gql.model.PropertyUnitInput;
import com.wh.gql.model.ReportingObject;
import graphql.execution.DataFetcherResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Controller
public class PropertyController {

    @QueryMapping
    public List<Property> properties() throws IOException, IllegalAccessException {
        return WhDb.getInstance().getAllProperties();
    }

    @QueryMapping
    public DataFetcherResult<Property> propertyByCode(@Argument String propertyCode) throws IOException {
        try {
            log.info("API called!!!");
            Optional<Property> optionalProperty = WhDb.getInstance().findPropertyByCode(propertyCode);
            log.info("RESPONSE IS: ");
            log.info(DataFetcherResult.<Property>newResult().data(optionalProperty.get()).build().getData().toString());
            return DataFetcherResult.<Property>newResult().data(optionalProperty.get()).build();
        } catch (WhPropertyNotFoundException exception) {
            return DataFetcherResult.<Property>newResult().
                    data(Property.ERROR_PROPERTY).
                    error(exception).
                    build();
        }
    }

    /**
     * Ommit/Comment-out this method to see effect of missing dataFetcher
     * for nested object PropertyUnit.
     *
     * @param property
     * @return
     * @throws IOException
     */
    @SchemaMapping(typeName = "Property", field = "unitTypes")
    public List<PropertyUnit> unitTypes(Property property) throws IOException {
        return WhDb.getInstance().getAllPropertyUnitsInProperty(property);
    }

    @MutationMapping
    public DataFetcherResult<PropertyUnit> addPropertyUnitToProperty(@Argument PropertyUnitInput propertyUnitInput,
                                                                     @Argument String propertyCode)
            throws IOException, IllegalAccessException {
        log.info("ADD GQL invoked");
        try {
            PropertyUnit propertyUnit = WhDb.getInstance().addPropertyUnitForProperty(propertyUnitInput, propertyCode).get();
            return DataFetcherResult.<PropertyUnit>newResult().data(propertyUnit).build();
        } catch (WhPropertyUnitInputIsInvalidException |
                 WhPropertyNotFoundException |
                 WhPropertyUnitInputPAXException |
                 WhPropertyUnitAlreadyExistsException exception) {
            log.info(exception.getClass().getName());
            log.info(exception.toString());
            return DataFetcherResult.<PropertyUnit>newResult().
                    data(PropertyUnit.ERROR_PROPERTY_UNIT).
                    error(exception).
                    build();
        }
    }

    @MutationMapping
    public DataFetcherResult<PropertyUnit> editPropertyUnitOfProperty(@Argument PropertyUnitInput propertyUnitInput,
                                                                      @Argument String propertyCode)
            throws IOException, IllegalAccessException {
        try {
            PropertyUnit propertyUnit = WhDb.getInstance().editPropertyUnitForProperty(propertyUnitInput, propertyCode).get();
            return DataFetcherResult.<PropertyUnit>newResult().data(propertyUnit).build();
        } catch (WhPropertyNotFoundException |
                 WhPropertyUnitInputIsInvalidException |
                 WhPropertyUnitInputPAXException |
                 WhPropertyUnitDoesNotExistException exception) {
            return DataFetcherResult.<PropertyUnit>newResult().
                    data(PropertyUnit.ERROR_PROPERTY_UNIT).
                    error(exception).
                    build();
        }
    }

    @MutationMapping
    public DataFetcherResult<String> deletePropertyUnitOfProperty(@Argument String propertyUnitCode,
                                                                  @Argument String propertyCode) throws IOException {
        try {
            WhDb.getInstance().deletePropertyUnitByUnitCodeAndPropertyCode(propertyUnitCode, propertyCode);
            return DataFetcherResult.<String>newResult().data("SUCCESS").build();
        } catch (WhPropertyUnitInputIsInvalidException |
                 WhPropertyNotFoundException |
                 WhPropertyUnitDoesNotExistException exception) {
            return DataFetcherResult.<String>newResult().
                    data("FAILURE").
                    error(exception).
                    build();
        }
    }

    @SubscriptionMapping("livePropertyCountForAll")
    public Flux<ReportingObject> livePropertyCountForAll() {
        return Flux.fromStream(
                Stream.generate(() -> {

                    // METHOD 1: Send a pulse every second
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }


                    // METHOD 2: Send ws message upon save/edit/delete when queue.take() unblocks
                    try {
                        String taken = WhDb.queue.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        return WhDb.getInstance().getAllWhPropertyUnits();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
    }

}
