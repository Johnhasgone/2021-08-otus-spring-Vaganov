package ru.otus.spring18homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class Spring18HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring18HomeworkApplication.class, args);
    }

}
