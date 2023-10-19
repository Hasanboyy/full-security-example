FROM openjdk:17
ADD build/libs/security-role-configuration-0.0.1-SNAPSHOT.jar app.jar
VOLUME /simple.app
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080