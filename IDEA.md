# Event-Driven Analytics Backend

EventOps is a high-performance, event ingestion and processing platform — think of it as a simplified backend engine for tools like Segment, Mixpanel, or Google Analytics. 
It allows external applications to send events (e.g., "user_clicked_button", "order_placed"), which are:

- Validated and ingested via an API
- Asynchronously processed and stored
- Made available for querying aggregated analytics
- This is backend-heavy, highly scalable, and lets you implement a wide array of advanced Java concepts.

<br>

### 🧱 Key Components

1. Event Collector API (Spring Boot)

- Receives events via REST or gRPC
- Validates structure and authentication (JWT)
- Publishes events to Kafka or RabbitMQ

2. Event Processor (Java)
- Consumes events from Kafka
- Writes them to the database (PostgreSQL)
- Performs deduplication, validation, maybe simple transformations

3. Query Service (Spring Boot)

- Exposes endpoints like:
- GET /events/count?eventType=order_placed
- GET /events/trend?interval=daily
- Queries aggregate tables (e.g., event_count_by_day)

4. Analytics Engine (Optional Go Service)

- Go microservice that processes streams for advanced analytics (e.g., rolling averages)
- Communicates via Kafka or gRPC

5. Monitoring & Observability

- Prometheus + Micrometer for metrics
- Grafana dashboards
- Logging via ELK stack or Loki

6. Auth Gateway (Spring Security + OAuth2)

- JWT-based client auth
- Token verification middleware

<br>

### ⚙️ Core Technologies

| Category       | Stack                                                                             |
| -------------- | --------------------------------------------------------------------------------- |
| **Langs**      | Java 21+, Go (for microservice)                                                   |
| **Frameworks** | Spring Boot 3, Spring Cloud, Spring Security, Micronaut/Quarkus (optional)        |
| **Messaging**  | Kafka (preferred), or RabbitMQ                                                    |
| **DB**         | PostgreSQL (events), Redis (caching), optional: ClickHouse or Druid for analytics |
| **API**        | REST (with Swagger/OpenAPI), optional gRPC                                        |
| **Monitoring** | Micrometer, Prometheus, Grafana, Loki                                             |
| **Infra**      | Docker, Docker Compose, GitHub Actions, AWS (ECS, RDS), K8s (optional)            |

<br>

### 🎓 Learning Path

| Skill                    | How You’ll Build It                                                                |
| ------------------------- | ---------------------------------------------------------------------------------- |
| ✅ Advanced Java & Spring | Modular design, Spring Security, Spring Cloud Config                               |
| ✅ Multithreading         | Build async consumers with thread pools, reactive event processors                 |
| ✅ JVM tuning             | Profile & tune heap/memory with tools like VisualVM, JFR                           |
| ✅ Messaging              | Kafka producers/consumers, retry logic, dead letter queues                         |
| ✅ Performance            | Benchmark event ingestion, optimize database indexing                              |
| ✅ Clean architecture     | Use ports/adapters (hexagonal), aggregate roots (DDD)                              |
| ✅ Testing mastery        | Unit, integration, contract (e.g. Pact) and load testing                           |
| ✅ DevOps                 | Docker, CI/CD pipeline, logging & monitoring                                       |
| ✅ Optional Go            | Write one microservice in Go (e.g. analytics processor) and connect via gRPC/Kafka |

<br>

### 🛠️ Roadmap Milestones

### 🟢 Phase 1: Setup, Design & Foundation

- Project planning
- Folder structure
- Basic working Spring Boot app
- Define core domain

📌 Tasks:

- Create GitHub repo: EventOps
- Setup event-collector module with Spring Boot 3
- Define a simple REST endpoint: POST /events
- Define Event model (e.g., name, timestamp, userId, metadata)
- Add basic validation & error handling
- Add PostgreSQL connection (using Spring Data JPA)
- Store event into DB (simple table)

🧠 Skills:

- Spring Boot setup
- JPA & PostgreSQL
- REST API structure
- Basic validation

<br>

### 🟡 Phase 2: Kafka & Async Processing

- Kafka integration
- Decouple event processing from ingestion
- Store raw events in DB via consumer

📌 Tasks:

- Add Kafka to docker-compose.yml
- Convert POST /events to push messages to Kafka
- Create event-processor module (separate Spring Boot app)
- Consume Kafka events, store in DB
- Log successful ingestion, handle errors

🧠 Skills:

- Kafka producers/consumers
- Spring Kafka
- Async message processing

<br>

### 🟠 Phase 3: Clean Architecture & Code Refactor

- Apply Hexagonal Architecture (ports/adapters)
- Prepare for scaling services

📌 Tasks:

- Separate domain models, services, and infrastructure adapters
- Move persistence to its own package/module
- Create interfaces for domain services
- Add test coverage with JUnit + Mockito

🧠 Skills:

- Domain-driven design (DDD)
- Clean modular architecture
- Testing strategies

<br>

### 🔵 Phase 4: Query Service & Analytics API

- Build query-service to expose analytics
- Enable basic aggregate queries

📌 Tasks:

- Build GET /events/count?eventType=...
- Add query for daily aggregates
- Write SQL views or scheduled jobs for aggregation
- Cache results with Redis for performance

🧠 Skills:

- SQL aggregation & optimization
- Caching (Redis)
- API design for reporting

<br>

### 🔴 Phase 5: Observability & Monitoring

- Add logs, metrics, tracing

📌 Tasks:
- Add Prometheus + Micrometer metrics
- Track event ingestion rate, errors, latencies
- Set up Grafana dashboard (Docker)
- Add structured logging (Logback or Logstash format)

🧠 Skills:

- Monitoring
- Observability mindset
- Instrumentation

<br>

### ⚫ Phase 6: Security & Multi-Tenant Auth

- Secure APIs
- Support multiple clients/projects

📌 Tasks:

- Add Spring Security with JWT support
- Implement clientId/clientSecret based login
- Store events per client
- Add rate-limiting per client (e.g., Bucket4j)

🧠 Skills:

- OAuth2 & JWT
- Access control
- API rate limiting

<br>

### ⚪ Phase 7 (Optional): Add Go Microservice

- Create Go service for data summarization

📌 Tasks:

- Set up small Go app to consume Kafka
- Process and emit summary events (e.g., “100 signups in 5 mins”)
- Store in Redis or expose via gRPC