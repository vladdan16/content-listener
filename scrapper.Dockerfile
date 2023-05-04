FROM maven:3.9.1-eclipse-temurin-11-alpine as build
WORKDIR /project
COPY . .
RUN mvn package -pl scrapper -am

FROM openjdk:17-oracle
WORKDIR /scrapper
COPY --from=build project/scrapper/target/scrapper-*.jar scrapper.jar
EXPOSE 8081
CMD ["java", "-jar", "scrapper.jar"]
