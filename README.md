# te0701

Fun project to set up renting tools at Home Depot or Lowes.

# Requirements

1. Customer rents tools for several days.
2. Customer API to create/modify/query customer
3. Rental API to create/modify/query rentals by customer
4. Store charges a daily rental fee (amount depends on the tool).
5. Some tools are free on weekends or holidays.
6. Clerk may give repeat customers a discount that is applied to the total daily charges to reduce the final charge.
7. REST API only

# Scenarios

## Customer does not have an account

1. Customer checks out a tool
2. Clerk makes a random decision about the customer discount
3. Application calls /v1/inventory/checkout
    * Tool code
    * Rental day count
    * Discount percent
    * Check out date
4. Console displays the rental agreement by calling /v1/inventory/checkout:export

## Customer creates an account (via website or phone app)

1. Application calls /v1/customers
    * Phone
    * First name
    * Last name
    * Email
    * Password

## Customer changes password

1. Application calls /v1/customers/{id}/change-password
   * password

## Customer does have an account

1. Customer checks out a tool
2. Customer or clerk enters phone number
3. Application calls /v1/customers/{phone}
4. Clerk terminal displays customers name
5. Application displays brief history of most recent transactions by calling /v1/customers/{id}/history
6. Clerk makes an informed decision about the customer discount based on account history
7. Application calls /v1/inventory/checkout
    * Tool code
    * Rental day count
    * Discount percent
    * Check out date
8. Console displays the rental agreement by calling /v1/inventory/checkout:export

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

Use Flyway to handle database schema changes. The database schema is found in src/main/resources/db/migration.

The developers use MySql. QA/UAT/Production can use AWS or GCP databases.

The Flyway configuration is part of the source code. A real project would have the Jenkins pipeline overwrite the
config file or use `mvn -Dflyway.configFiles=customConfig.conf` to provide a configuration that developers never see.

Flyway commands

```bash
mvn flyway:clean    -- removes the database schema
mvn flyway:migrate  -- create/update the database schema
```

A store in Hawaii or New York charges more than a store in Oklahoma.  
The store id is used as part of a composite index to retrieve the data for that store.

Store administration is not part of this project.

## Run the Application

Run the following command in a terminal window:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Test

run `mvn clean verify -Dspring.profiles.active=dev` to run all JUnit tests.

JaCoCo creates the [test coverage reports](./target/site/jacoco/index.html)

The current test environment utilizes SpringBootTest, JsonTest and Mockito to achieve a 93% test coverage.

### IntelliJ Configuration

Edit the JUnit test configuration so that tests automatically add the active Spring profile:

1. Click on "Edit Configurations"
2. Click on "Edit configuration templates ..."
3. Chose JUnit
4. Add "spring.profiles.active=dev" to the environment variables

## OpenApi 3.0 (Swagger)

Swagger/OpenApi provides the API documentation of the REST endpoints.  
Run the application and point a browser to http://localhost:8080/swagger-ui.html

## Actuators

The dev profile opens all actuators. The other environments only allow the health actuators.

Use http://localhost:8080/actuator with the dev profile to see all actuator links.

Use [flyway actuator](http://localhost:8080/actuator/flyway) to verify database migrations.

## Security

There are multiple ways to handle making the application secure.

* Use [Spring Boot security](https://spring.io/guides/gs/securing-web)
* The UI could talk to Firebase to handle user authentication
* Use OAuth or SAML
* Roll your own framework and use the request filter chain to check for the presence of a valid header
* Grovel before Google and choose a different option

## Audit

Auditing is a future requirement.

Auditing can be achieved by either

1. Placing an audit event on a message queue and a separate system handles the events
2. Table data contains a create employee id, update employee id, create date and update date

Unfortunately table data only shows the final time a row was updated. Audit events are preferred.

# Future Enhancements

1. Provide a sale endpoint that allows a manager to reduce the price of a tool code for a specified period of days.
2. Provide a tool maintenance/repair endpoint to allow temporary inventory reduction while a tool is in the shop.
3. Allow customer to rent multiple ladders at one time instead of making multiple checkout requests.