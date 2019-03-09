# Robotic Cleaner 

## Feature description
_Write a Java based web service that navigates an imaginary robotic cleaner through an oil spill in the sea._

## Tech
- Java 8
- Spring Boot 2.1.3
- JUnit 5

## How to run
To start the server, simply execute `./gradlew bootRun` command.

To run the tests, execute `./gradlew test` command.

### Endpoints available

- `POST /api/navigator/clean`: execute clean operation passing expected `body` as json.

Body request example:

```json
{
  "areaSize" : [5, 5],
  "startingPosition" : [1, 2],
  "oilPatches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "navigationInstructions" : "NNESEESWNWW"
}
```

Response example:
```json
{
  "finalPosition" : [1, 3],
  "oilPatchesCleaned" : 1
}
```