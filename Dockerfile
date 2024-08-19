FROM maven:3.8.6-openjdk-11 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /target/spring-0.0.1-SNAPSHOT.jar spring.jar
EXPOSE 7979
ENTRYPOINT ["java","-jar","spring.jar"]