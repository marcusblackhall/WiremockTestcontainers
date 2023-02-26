# Wiremock with Testcontainers

A project showing how to mock rest requests with use of Wiremock and Testcontainers.

## Dependencies
Docker should be running locally on your machine. The project runs under Java 11 and the only dependencies are for testcontainers and testcontainers junit5.

## Setup

FetchUser is a class that fetches user data using a java web client against a real url. The real url api is provided by[ https://reqres.in](https://reqres.in)
A wiremock mock response for the request is created in the folder ***src/main/resources/wiremock/mappings***.

In test class FetchUserIT a wiremock container is started and its ***/home/wiremock*** folder is mapped to the project src/main/resources/wiremock folder.

## Test run

When test FetchUserIT is run a wiremock container is created with testcontainers that exposes port 8080 and the volume /home/wiremock.

The Test then sets the url in the fetch user class to use the port and host mapped by testcontainers for the wiremock container.

The test checks that the response is the same as the one that is defined in the src/main/resources/wiremock folder.

To run the test use ...
```
mvn verify
```