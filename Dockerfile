FROM openjdk:23
LABEL authors="Juan Diego"
WORKDIR /app
COPY target/semestreservice-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "semestreservice-0.0.1-SNAPSHOT.jar"]