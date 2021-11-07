package ru.otus.spring08homework;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties
public class Spring08HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring08HomeworkApplication.class, args);
    }

}
