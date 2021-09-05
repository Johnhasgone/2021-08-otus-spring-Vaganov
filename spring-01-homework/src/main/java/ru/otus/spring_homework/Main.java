package ru.otus.spring_homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;
import ru.otus.spring_homework.service.QuestionService;

public class Main {
    public static void main(String[] args) throws GetTestQuestionException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        service.printQuestions();
    }
}
