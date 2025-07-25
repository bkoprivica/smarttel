# Build stage
FROM maven:3.8-openjdk-11 AS build
WORKDIR /build
# Copy the entire project so common modules can be built
COPY . ./
RUN mvn -pl notification-service -am clean package -DskipTests

# Run stage
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /build/notification-service/target/notification-service-1.0.0.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
