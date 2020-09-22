FROM openjdk:8
RUN mkdir /app
RUN mkdir /app/secrets
ADD target/weather-demo-0.0.1-SNAPSHOT.jar /app/app.jar

### change permission to grant arbitary user privilege to /app directory
RUN chgrp -R 0 /app
RUN chmod -R g=u /app

WORKDIR /app
ENV JAVA_OPTS=""

ENTRYPOINT exec java $JAVA_OPTS -Dhttps.protocols=TLSv1.1,TLSv1.2 -Djava.security.egd=file:/dev/./urandom -javaagent:/app/dd-java-agent.jar
