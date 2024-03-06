package org.example.dto;

import lombok.Data;

@Data
public class GraphQLQuery
{
    private String operationName;
    private Object variables;
    private String query;
}
