package com.wh.gql.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.util.ArrayList;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class PropertyUnit {

    public static final PropertyUnit ERROR_PROPERTY_UNIT = PropertyUnit.builder().
            id(new UUID(0L, 0L)).
            code("").
            name("").
            unitType("").
            minimumPersons(-1).
            maximumPersons(-1).
            build();

    /**
     *     GQL:
     *     -- type PropertyUnit --
     *     id: ID!
     *     # Short identifier code
     *     code: String!
     *     name: String!
     *     # Unit type, e.g. room, villa, apartment etc.
     *     unitType: String!
     *     minimumPersons: Int!
     */

    @NonNull
    private UUID id;

    @NonNull
    private String code;

    @NonNull
    private String name;

    @NonNull
    private String unitType;

    @NonNull
    private int minimumPersons;

    @NonNull
    private int maximumPersons;

}
