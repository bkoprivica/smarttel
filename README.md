Smarttel Microservices
Overview

This project contains four Spring Boot services that communicate over messages:

    customer-service – stores customer data and publishes events when a new customer is created.

    billing-service – listens for customer events and creates a billing record for each new account.

    notification-service – sends simple notifications whenever a customer is created.

    api-gateway – fronts the other services and exposes a single entry point.

Prerequisites

    Java 11 and Maven 3.8+

    Docker for building images

    Access to a Kubernetes cluster

Building

Each service is built independently. Build the shared module first or build from the project root:

mvn -f common-dto/pom.xml install
mvn -f customer-service/pom.xml package
mvn -f billing-service/pom.xml package
mvn -f notification-service/pom.xml package
mvn -f api-gateway/pom.xml package

To create Docker images, run the provided Dockerfiles:

docker build -t customer-service:latest ./customer-service
docker build -t billing-service:latest -f billing-service/Dockerfile .
docker build -t notification-service:latest -f notification-service/Dockerfile .
docker build -t api-gateway:latest ./api-gateway
Deployment

Apply the manifests in the k8s/ directory to deploy the services:

kubectl apply -f k8s/

The files create deployments for each service and expose them with load balancer services.
Running Services Locally

A RabbitMQ broker must be available on localhost:5672 for the services to communicate using Spring Cloud Stream. You can start a RabbitMQ container with Docker Compose:

docker-compose up -d rabbitmq

After RabbitMQ is running, start each service locally as needed:

cd customer-service
mvn spring-boot:run

cd ../billing-service
mvn spring-boot:run

cd ../notification-service
mvn spring-boot:run

cd ../api-gateway
mvn spring-boot:run