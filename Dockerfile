FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/gestion-station-ski-1.0-RELEASE.jar gestion-station-ski-1.0-RELEASE.jar
ENTRYPOINT ["java","-jar","/docker-spring-boot.jar"]