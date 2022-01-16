package ru.otus.spring14homework;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Spring14HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring14HomeworkApplication.class, args);
    }
}
