FROM openjdk:8-jdk-alpine

ADD ./ /demo
WORKDIR /demo

RUN ./mvnw package

EXPOSE 8080
ENTRYPOINT ["java","-jar","target/webhook-0.0.1-SNAPSHOT.jar"]
