FROM openjdk:16-jdk-alpine

COPY target/PerrysChatApp*.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app.jar"]