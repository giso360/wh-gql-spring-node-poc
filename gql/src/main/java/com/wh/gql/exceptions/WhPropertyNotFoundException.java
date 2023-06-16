package com.wh.gql.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class WhPropertyNotFoundException extends WhGenericGQLException {
    public WhPropertyNotFoundException(String requestedPropertyCode) {
        super(String.format("Property %s not found", requestedPropertyCode));
    }

}
