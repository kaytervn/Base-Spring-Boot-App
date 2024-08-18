FROM maven:3.8.6-openjdk-11 as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 7979
ENTRYPOINT ["java","-jar","demo.jar"]