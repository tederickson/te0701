# te0701
Fun project to set up renting tools at Home Depot or Lowes.

# Requirements
1. Customer rents tools for several days.
2. Customer API to create/modify/query customer
3. Rental API to create/modify/query rentals by customer
2. Store charges a daily rental fee (amount depends on the tool).
3. Some tools are free on weekends or holidays.
4. Clerk may give repeat customers a discount that is applied to the total daily charges to reduce the final charge.
5. REST API only


## Config
The application.properties file is stored in Git.  
The rest are usually local files to prevent leaking sensitive information.

* src/main/resources/application.properties
    * Common configuration values
* src/main/resources/application-dev.properties
    * Development configuration values
* flyway.conf
    * Flyway configuration

## Database
Use Flyway to handle database schema changes.  The database schema is found in src/main/resources/db/migration.

The developers use MySql.  QA/UAT/Production can use AWS or GCP databases.

The Flyway configuration is part of the source code.  A real project would have the Jenkins pipeline overwrite the 
config file or use `mvn -Dflyway.configFiles=customConfig.conf` to provide a configuration that developers never see.

Flyway commands
```bash
mvn flyway:clean    -- removes the database schema
mvn flyway:migrate  -- create/update the database schema
```

## Run the Application
Run the following command in a terminal window:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Test

run `mvn clean verify -Dspring.profiles.active=dev` to run all JUnit tests.

JaCoCo creates the [test coverage reports](./target/site/jacoco/index.html)

The current test environment utilizes SpringBootTest, JsonTest and Mockito to achieve a 93% test coverage.

## OpenApi (Swagger)
Swagger provides the API documentation of the REST endpoints.  
Run the application and point a browser to http://localhost:8080/swagger-ui.html

## Actuators
The dev profile opens all actuators.  The other environments only allow the health actuators.

Use http://localhost:8080/actuator with the dev profile to see all actuator links.

Use [flyway actuator](http://localhost:8080/actuator/flyway) to verify database migrations.

## Security
There are multiple ways to handle making the application secure.
* Use [Spring Boot security](https://spring.io/guides/gs/securing-web)
* The UI could talk to Firebase to handle user authentication
* Use OAuth or SAML
* Roll your own framework and use the request filter chain to check for the presence of a valid header