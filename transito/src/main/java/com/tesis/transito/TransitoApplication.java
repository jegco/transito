package com.tesis.transito;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@ComponentScan("com.tesis.dominio")
@ComponentScan("com.tesis.persistencia")
@EnableMongoAuditing
@EnableReactiveMongoRepositories(basePackages = {"com.tesis.persistencia.repositorios"})
@SpringBootApplication
public class TransitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransitoApplication.class, args);
    }

    @Autowired
    MongoClient mongoClient;

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient, "transito");
    }

}
