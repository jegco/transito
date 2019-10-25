package com.tesis.transito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@ComponentScan("com.tesis.dominio")
@EnableMongoAuditing
@EnableReactiveMongoRepositories
public class TransitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransitoApplication.class, args);
	}

}
