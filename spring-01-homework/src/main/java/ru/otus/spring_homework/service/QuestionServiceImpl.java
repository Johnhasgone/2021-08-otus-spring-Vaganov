package ru.otus.spring_homework.service;

import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import ru.otus.spring_homework.dao.QuestionsDaoImpl;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private QuestionsDaoImpl dao;

    @Override
    public void printQuestions() throws CsvException, IOException {
        List<String[]> questions = dao.getTestQuestions();

        int i = 1;
        for (String[] question : questions) {
            System.out.println("Question " + i + ":");
            System.out.println(question[0]);
            System.out.println();
            System.out.println("Choose the answer(s):");
            for (int j = 1; j < question.length; j++) {
                System.out.println((char) (64 + j) +". " + question[j]);
            }
            System.out.println();
            i++;
        }
    }
}
