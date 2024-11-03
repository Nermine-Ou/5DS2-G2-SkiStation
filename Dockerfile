
FROM openjdk:11
EXPOSE 8089
ADD target/StationSki-1.0.jar StationSki-1.0.jar
ENTRYPOINT ["java", "-jar", "StationSki-1.0.jar"]