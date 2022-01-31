FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/home/runner/work/weather-demo/weather-demo/target/weather-demo-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
