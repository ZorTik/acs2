FROM gradle:8.13-jdk17-alpine AS build

COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

RUN gradle clean :acs-server:build --no-daemon

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /home/gradle/project/acs-server/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]