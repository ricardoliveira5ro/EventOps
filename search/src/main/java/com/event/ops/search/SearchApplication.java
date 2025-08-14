package com.event.ops.search;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"com.event.ops.search","com.event.ops.common","com.event.ops.auth"})
@EntityScan(basePackages = "com.event.ops.database.entity")
@PropertySource({"classpath:security.properties","classpath:redis.properties"})
@EnableCaching
public class SearchApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./search").load();

		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
		System.setProperty("REDIS_USERNAME", dotenv.get("REDIS_USERNAME"));
		System.setProperty("REDIS_PASSWORD", dotenv.get("REDIS_PASSWORD"));

		SpringApplication.run(SearchApplication.class, args);
	}

}
