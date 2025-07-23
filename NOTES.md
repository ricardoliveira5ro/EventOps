### Kafka

- This pipeline `event-collector` collect multiple events sent by users/systems and Kafta process them asynchronously so this backend doenst have to be blocked waiting
- Use `application.properties` and Spring Boot Auto-Configure Kafka
- Maven dependency `spring-kafka`
- Hosting in `confluent.cloud`

### Environment Variables

- Maven dependency `dotenv-java`
- Import variables on application runner