FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/docker-spring-boot.war docker-spring-boot.jar
ENTRYPOINT ["java","-jar","/docker-spring-boot.jar"]