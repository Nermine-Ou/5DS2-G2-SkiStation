FROM openjdk:17-jdk-slim
EXPOSE 8083
ADD target/gestion-station-ski-0.0.3-SNAPSHOT.war gestion-station-ski-0.0.3-SNAPSHOT.war
ENTRYPOINT ["java", "-jar", "/gestion-station-ski-0.0.3-SNAPSHOT.war"]
