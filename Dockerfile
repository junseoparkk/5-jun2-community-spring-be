FROM openjdk:17-jdk-slim

ARG JAR_FILE=build/libs/*.jar
ARG PROFILES
ARG ENV

ENV SPRING_PROFILES_ACTIVE=${PROFILES}
ENV SERVER_ENV=${ENV}

WORKDIR /app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-Dserver.env=${SERVER_ENV}", "-jar", "app.jar"]
