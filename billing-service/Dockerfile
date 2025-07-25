# Build stage
FROM maven:3.8-openjdk-11 AS build
WORKDIR /build
# Copy the entire project to enable building module dependencies
COPY . ./
RUN mvn -pl billing-service -am clean package -DskipTests

# Run stage
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /build/billing-service/target/billing-service-1.0.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
