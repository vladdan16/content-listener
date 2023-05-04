FROM maven:3.9.1-eclipse-temurin-11-alpine as build
WORKDIR /project
COPY . .
RUN mvn package -pl bot -am

FROM openjdk:17-oracle
WORKDIR /bot
COPY --from=bild project/bot/target/bot-*.jar bot.jar
EXPOSE 8080
CMD ["java", "-jar", "bot.jar"]
