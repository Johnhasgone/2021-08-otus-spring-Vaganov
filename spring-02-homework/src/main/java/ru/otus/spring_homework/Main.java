package ru.otus.spring_homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;
import ru.otus.spring_homework.service.QuestionService;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuestionService service = context.getBean(QuestionService.class);
        service.performTest();
    }
}
