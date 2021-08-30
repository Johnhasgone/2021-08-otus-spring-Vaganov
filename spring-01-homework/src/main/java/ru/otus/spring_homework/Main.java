package ru.otus.spring_homework;

import com.opencsv.exceptions.CsvException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring_homework.service.QuestionService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        service.printQuestions();
    }
}
