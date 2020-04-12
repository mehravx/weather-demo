# Weather App Demo

* [Internal Documentation](./documentation)


## Pre-requisites

### Software

The following software should be installed locally:

* [Java 8](https://github.com/ojdkbuild/ojdkbuild)
* [Maven](http://maven.apache.org/)

## Execution Instructions (Command Line)

* Build & Package

> `mvn clean package`

* Run the application

> `mvn spring-boot:run`

OR

> `java -jar target/weather-demo-0.0.1-SNAPSHOT.jar`

* Copy the port from the logs..

> `2020-04-12 02:58:17.177  INFO 57960 --- [           main] o.s.b.w.e.t.TomcatWebServer              : Tomcat started on port(s): 65303 (http) with context path ''`

* Run unit tests

> `mvn clean test`

* Run unit & integration tests

> `mvn clean verify`


