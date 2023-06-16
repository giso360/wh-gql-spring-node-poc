package com.wh.gql.controller;

import com.wh.gql.db.WhDb;
import com.wh.gql.db.WhRepository;
import com.wh.gql.model.Property;
import graphql.Assert;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.GraphQlTesterAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@GraphQlTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void propertyByCode() {
    }

    @Test
    void properties() {
        // language=GraphQL
        String q = """
                    query {
                    properties {
                            type
                            code
                            name
                            unitTypes {
                            name                        
                                            }        
                        }
                    }
                """;
        GraphQlTester.Response response = graphQlTester.document(q).execute();
        Assertions.assertEquals(2, response.path("properties").entityList(Property.class).get().size());
    }

    @Test
    void unitTypes() {
    }

    @Test
    void addEditPropertyUnitToProperty() {
    }

    @Test
    void addPropertyUnitToProperty() {
    }

    @Test
    void editPropertyUnitOfProperty() {
    }

    @Test
    void deletePropertyUnitOfProperty() {
    }
}