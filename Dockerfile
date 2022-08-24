FROM openjdk:17-oracle 
ADD target/routine-0.0.1-SNAPSHOT.jar routine-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "routine-0.0.1-SNAPSHOT.jar"]

