FROM openjdk:17-orcle
WORKDIR /scrapper
COPY target/scrapper-*.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]