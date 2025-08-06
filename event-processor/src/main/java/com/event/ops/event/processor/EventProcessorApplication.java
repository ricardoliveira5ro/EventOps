package com.event.ops.event.processor;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.event.ops.event.processor","com.event.ops.common"})
@EntityScan(basePackages = "com.event.ops.database.entity")
@EnableCaching
public class EventProcessorApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./event-processor").load();

		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		System.setProperty("KAFKA_USERNAME", dotenv.get("KAFKA_USERNAME"));
		System.setProperty("KAFKA_PASSWORD", dotenv.get("KAFKA_PASSWORD"));

		System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
		System.setProperty("REDIS_USERNAME", dotenv.get("REDIS_USERNAME"));
		System.setProperty("REDIS_PASSWORD", dotenv.get("REDIS_PASSWORD"));

		SpringApplication.run(EventProcessorApplication.class, args);
	}

}
