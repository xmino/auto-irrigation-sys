# Automatic Irrigation Exercise
## Rest Services for plots of Lands

### Requirements:

Your APIs should provide the following functionality to downstream consumers of the API (in a RESTful way):

#### Plot of land
▪ Add new plot of land

▪ Configure a plot of land

▪ Edit a plot of land

▪ List all plots and it's details

▪ Integration interface with the sensor device once a plot of land
need to be irrigate

▪ Update the status of the slot once the request is successfully sent
to the sensor device

▪ Retry calls to the sensor device in case the sensor not available
(pre configured )

▪ Alerting system to be implemented in case the sensor not
available and after exceeding the retry times.

### Description:

- Microservice made with Java 11, SpringBoot, Maven, Flyway,  H2 database, lombok, junit y AWS sqs.
- Architecture: Domain Driven Design (DDD) using Onion arq with 3 layers:
  - ( Infrastructure -> ( Api -> ( Domain ) ) )
- Builder pattern.
- Database schemas and seeded data on resources->db->migration using Flyway
- Unit Tests
- RestServices Integration Tests

### Assumptions 

- The integration interface with the sensor is a Mock Rest Service (RestIrrigationSystem)
- The alert system is event driven, so the microservice will publish in a queue (SQS) subscribed a notification service (SNS) 
which would be integrated to any kind of alerts (email, text message, slack, etc)
- The SQS in this example is fictitious but will work with any valid uri. 
- Pre configured values live in properties

### How to run

- compiled in Java 11
- needs MVN
- clone: 
  - gh repo clone xmino/auto-irrigation-sys
  - https://github.com/xmino/auto-irrigation-sys.git
  - git@github.com:xmino/auto-irrigation-sys.git
- Mvn:
  - mvn clean install
  - mvn spring-boot:run
  - mvn test

### Tests Examples

- Test with Postman or Similar

- Api Doc reference
  - https://documenter.getpostman.com/view/11548558/2s8YsqWEs8

```
curl --location --request GET 'localhost:8080/auto-irrigation/v1/plots' \
--header 'Content-type: application/json' \
--data-raw ''

curl --location --request POST 'localhost:8080/auto-irrigation/v1/plots' \
--header 'Content-type: application/json' \
--data-raw '{
    "name":"My Plot",
    "cultivatedArea":"1000",
    "crop":"FOOD"
}'

curl --location --request PUT 'localhost:8080/auto-irrigation/v1/plots/4fc63668-6a38-11ed-a1eb-0242ac120002' \
--header 'Content-type: application/json' \
--data-raw '{
    "name":"My Plot Edited",
    "cultivatedArea":"1010",
    "crop":"FOOD"
}'

curl --location --request POST 'localhost:8080/auto-irrigation/v1/plots/configure/4fc63668-6a38-11ed-a1eb-0242ac120002' \
--header 'Content-type: application/json' \
--data-raw '{
    "init":1604531772,
    "durationMinutes":"20",
    "amountWater":"100",
    "status":"PENDING"
}'
