package com.wh.gql.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Jacksonized
@Builder
public class Property {

    @JsonCreator
    public Property(String code, String name, String type, int rating, List<PropertyUnit> propertyUnits) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.propertyUnits = propertyUnits;
    }





    public static final Property ERROR_PROPERTY = Property.builder().
            code("").
            name("").
            type("").
            rating(-1).
            propertyUnits(new ArrayList<>()).
            build();


    /**
     *     GQL
     *     -- type Property --
     *     code: String!
     *     name: String!
     *     type: String
     *     rating: Int
     *     unitTypes: [PropertyUnitType]!
     */
    @NonNull
    private String code;

    @NonNull
    private String name;

    private String type;
    private int rating;

    @NonNull
    private List<PropertyUnit> propertyUnits;

}
