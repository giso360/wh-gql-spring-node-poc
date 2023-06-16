# WH GQL SPRING NODE POC

------

This repo contains a collection services implemented in spring-boot and node.js frameworks.



### COMPONENTS

------

The software components developed are described in the table below:

| SW COMPONENT                       | ROLE                                                         | TECH                        |
| ---------------------------------- | ------------------------------------------------------------ | --------------------------- |
| gql                                | GQL server app. Contains all data inside an in-memory List collection | java/spring-boot            |
| gql-wh-client                      | GQL client app that simulates a customer sending add and delete requests to the server | java/spring-boot            |
| gql-wh-client-node                 | GQL client app that simulates a customer sending add and delete requests to the server | JS/node.js - uses fetch API |
| wh-spring-boot-subscription-client | GQL client for subscriptions via websocket                   | java/spring-boot            |



### HOW TO RUN

------



- To run spring-boot apps, navigate to their respective root folder where pom.xml is located and run the command:

    `mvnw spring-boot:run`

    Alternatively, open project in IntelliJ and run project from IDE utilities

- To run node apps, navigate to root folder where package.json file is located. Run the following commands:

  `npm i`

  `node index.js`

  

### API

------

Once the gql SW component is launched, queries and mutations are available from http://localhost:8080/wh using GQL playground. For the subscription requests, the API is available here: http://localhost:8080/graphiql?path=/wh&wsPath=/wh