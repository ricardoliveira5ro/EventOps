package com.event_ops.event_collector;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventCollectorApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./event-collector").load();

		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(EventCollectorApplication.class, args);
	}

}
