# TEMP TRIGGER

## Running Services Locally

A RabbitMQ broker must be available on `localhost:5672` for the services to communicate using Spring Cloud Stream. You can start a RabbitMQ container using Docker Compose:

```bash
docker-compose up -d rabbitmq
```

After RabbitMQ is running, the notification service can be started with Maven:

```bash
cd notification-service
mvn spring-boot:run
```
