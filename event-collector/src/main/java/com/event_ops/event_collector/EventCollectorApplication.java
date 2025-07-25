package com.event_ops.event_collector;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventCollectorApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./event-collector").load();

		System.setProperty("KAFKA_USERNAME", dotenv.get("KAFKA_USERNAME"));
		System.setProperty("KAFKA_PASSWORD", dotenv.get("KAFKA_PASSWORD"));

		SpringApplication.run(EventCollectorApplication.class, args);
	}

}
