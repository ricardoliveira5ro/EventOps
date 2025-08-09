package com.event.ops.auth;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.event.ops.auth","com.event.ops.common.exception"})
@EntityScan(basePackages = "com.event.ops.database.entity")
public class AuthApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./auth").load();

		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(AuthApplication.class, args);
	}

}
