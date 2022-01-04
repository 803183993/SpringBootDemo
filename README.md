# Welcome to Spring Boot Demo

This project helps you navigate the Spring Boot framework for the following use cases:

- _Rest services_
- _Messaging with JMS_
- _Database persistence with Hibernate_

The project also shows how a two-phase commit transaction manager, like Atomikos, can be used within Spring Boot framework.

## Requirements

The project requires [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or
higher. And an SQL based DB - for running integration tests.

## Add Oracle JDBC driver Manually

Note

Due to Oracle license restrictions, the Oracle JDBC driver is not available in the public Maven repository. To use the Oracle JDBC driver with Maven, you have to download and install it into your Maven local repository manually.

```console
$ mvn install:install-file -Dfile=<ORACLE_PATH>\ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jar
```

The project makes use of Maven 3.5.0 or higher.

## Create DB Schema

Use <HOME>\SpringBootDemo\persistence\src\main\resources\1-create-tables-baseline.sql.vm to create the tables and user.

## Running the Application

Running the application is as simple as using the command:

> `java -jar [-Dspring.profiles.active=<profileName>] application-[version].jar`

On a unix system you would likely produce a script file that would run the command with a "nohup".

The application will then startup producing the following output:

### Run Tests

Endpoint

```console
$ curl "http://localhost:8080/rest/getPerson/802194884"
```

```console
$ curl -X POST http://localhost:8080/rest/addPerson -H "Content-Type: application/json" -d @C:\dev\spring-boot\test-data\person.json 
```


#//(exclude = {DataSourceAutoConfiguration.class})

