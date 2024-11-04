FROM openjdk:17-jdk-slim
EXPOSE 8083
ADD target/StationnSki-0.0.3-RELEASE.war StationnSki-0.0.3-RELEASE.war
ENTRYPOINT ["java", "-jar", "StationnSki-0.0.3-RELEASE.war"]