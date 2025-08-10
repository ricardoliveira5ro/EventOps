package com.event.ops.event.collector;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"com.event.ops.event.collector","com.event.ops.common.exception","com.event.ops.common.security"})
@PropertySource("classpath:security.properties")
public class EventCollectorApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./event-collector").load();

		System.setProperty("KAFKA_USERNAME", dotenv.get("KAFKA_USERNAME"));
		System.setProperty("KAFKA_PASSWORD", dotenv.get("KAFKA_PASSWORD"));

		SpringApplication.run(EventCollectorApplication.class, args);
	}

}
