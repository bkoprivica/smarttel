# Smarttel Microservices

## Overview

This project contains four Spring Boot services that communicate over messages:

- **customer-service** – stores customer data and publishes events when a new customer is created.
- **billing-service** – listens for customer events and creates a billing record for each new account.
- **notification-service** – sends simple notifications whenever a customer is created.
- **api-gateway** – fronts the other services and exposes a single entry point.

## Prerequisites

- Java 11 and Maven 3.8+
- Docker for building images
- Access to a Kubernetes cluster

## Building

Each service is built independently:

```bash
mvn -f customer-service/pom.xml package
mvn -f billing-service/pom.xml package
mvn -f notification-service/pom.xml package
mvn -f api-gateway/pom.xml package
```

To create Docker images, run the provided Dockerfiles:

```bash
docker build -t customer-service:latest ./customer-service
docker build -t billing-service:latest ./billing-service
docker build -t notification-service:latest ./notification-service
docker build -t api-gateway:latest ./api-gateway
```

## Deployment

Apply the manifests in the `k8s/` directory to deploy the services:

```bash
kubectl apply -f k8s/
```

The files create deployments for each service and expose them with load balancer services.
