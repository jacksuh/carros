FROM openjdk:8

WORKDIR /app

COPY target/carros.jar  /app/docker-carros-app.jar

ENTRYPOINT ["java","-jar","docker-carros-app.jar"]
