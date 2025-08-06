### Kafka

- This pipeline `event-collector` collect multiple events sent by users/systems and Kafta process them asynchronously so this backend doenst have to be blocked waiting
- Use `application.properties` and Spring Boot Auto-Configure Kafka
- Maven dependency `spring-kafka`
- Hosting in `confluent.cloud`

### Flyway

- Files should follow prefix convention `V[1]__`
- Run `mvn flyway:migrate -D flyway.url=[URL] -D flyway.user=[USER] -D flyway.password=[PASSWORD]`
- Change hibernate to validate and not update `spring.jpa.hibernate.ddl-auto=validate`

### Cache / Redis

- `@EnableCaching` on the application runner
- `@Cacheable(value = "dailyAggregate", key = "#p0")` annotation to try to use cache, `p0` means parameter 0 (first)
- `@CacheEvict(value = "dailyAggregate", key = "#event.eventName")` annotation to clear existent cache for this key
- `@CachePut(value = "dailyAggregate", key = "#event.eventName")` annotation to update cache
- Some data types i.e. `java.time.LocalDate` need to be registered as JavaTimeModule for Jackson in object mapper. In this case it was simpler to cache the date as string

### Environment Variables

- Maven dependency `dotenv-java`
- Import variables on application runner

### Unit Testing

- Use `@Autowired` with beans you don't manage or when the real implementation is required
- Use `MockitoBean` to mock dependencies of the class

### Coverage

- Maven dependency `org.jacoco`
- Run `mvn clean test`
- Argument `-D spring.profiles.active=dev` if needed
- Check results in `target/site/jacoco/index.html` on browser i.e.