# EvChargingAPI

This is a Java Spring Boot application that provides APIs to perform CRUD operations on the EV-Stations. It uses Swagger to document the APIs.

## Getting Started
### Requirements
- Java 19
- MySQL database
- Spring Boot 3.0.4
### Installation
1. Clone this repository to your local machine using git clone
    ```bash
    https://github.com/Dr-Dreams/EVChargingStationApi.git
    ```
2. Navigate to the project directory:
    ```bash
    cd EVChargingStationApi
    ```
3. Open the project in your IDE(Suggestion : - Intellij IDEA, VS-CODE)

    - [Intellij](https://www.jetbrains.com/idea/download/#section=mac)
    - [VS-CODE](https://code.visualstudio.com/download)

4. Open application.properties
    ```bash
   /src/main/resoucres
    ```
5. Set up your MySQL database and update the application.properties file with your database details
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/DataBaseName
    spring.datasource.username=user
    spring.datasource.password=pass
    ```
6. Run the application
    ```
    mvn spring-boot:run
    ```
7. The application should now be accessible at
    ```
    http://localhost:8080/api-docs
    or
    http://localhost:8082/swagger-ui/index.html
    ```
### API Endpoints
- GET /api/v1/stations: Get a list of all charging stations.
- GET /api/v1/stations/show/{id}: Get details of a specific charging station by ID.
- POST /api/v1/stations: Create a new charging station with a station image and pricing information.
- PUT /api/v1/stations/{id}/edit: Update an existing charging station by ID.
- DELETE /api/v1/stations/delete/{id}: Delete an existing charging station by ID.
- GET /api/v1/stations?limit=value: Get a list of {value} number of charging stations.
- GET /api/v1/stations?sort={sort}&param={param}: Get a list of charging stations sorted by a specified parameter and sorting order.
### Built With
- Spring Boot - The web framework used
- Maven - Dependency Management
- MySQL - Database used
- OpenAPI - API documentation
### DataBase Model Parameters
[stationId, stationName, stationImage, stationPricing, stationAddress]
### Authors
- [Mausam (Dr-Dreams)](https://github.com/Dr-Dreams)
