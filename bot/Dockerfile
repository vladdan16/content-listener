FROM openjdk:17-orcle
WORKDIR /bot
COPY target/bot-*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]