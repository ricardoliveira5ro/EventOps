# EventOps

Learning project, backend heavy, built to explore advanced concepts in **Spring Boot**, **Kafka**, **PostgreSQL**, **Redis**, and **clean architecture**.

### 📖 Overview

Microservice architecture to event ingestion and processing system. Multiple Maven modules to keep concerns separated.

1. Clients send events to the system
2. Events are published asynchronously to Kafka
3. Processors consume events, transform them, and store them in database
4. Clients can later query and analyze their events with caching support

### 🧩 Modules

- *auth*: Provides user registration and login with client key and secret value pair to obtain JWT auth token
- *common*: Shared library containing, security beans for authorization & rate limiting, global exception handling, and redis configuration
- *database*: Database-related infrastructure such as flyway migration scripts spring boot entities
- *event-collector*: Service for event ingestion service and publish to Kafka producer
- *event-processor*: Consumes events from Kafka, transforms them into entities and saves them to database
- *search*: Exposes REST endpoints to query stored events, including caching with Redis for faster lookups

### 📚 Tech Stack

- Java 21 + Spring Boot 3.5.4
- Kafka (consumer concurrency for parallel message processing)
- PostgreSQL + JPA
- Flyway
- Redis
- Spring Security + JWT
- Spring Actuator + Prometheus
- Spring AOP (logging)
- JUnit + Jacoco