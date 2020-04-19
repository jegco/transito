package com.tesis.transito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableMongoAuditing
@EnableReactiveMongoRepositories(basePackages = {"com.tesis.transito.persistencia.repositorios"})
@SpringBootApplication
public class TransitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransitoApplication.class, args);
    }
}
