package ru.otus.spring_homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring_homework.service.QuestionService;

@SpringBootApplication
public class Spring03HomeworkApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Spring03HomeworkApplication.class, args);
        QuestionService service = context.getBean(QuestionService.class);
        service.performTest();
    }

}
