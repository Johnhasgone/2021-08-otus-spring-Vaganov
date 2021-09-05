package ru.otus.spring_homework.service;

import lombok.AllArgsConstructor;
import ru.otus.spring_homework.dao.QuestionsDao;
import ru.otus.spring_homework.domain.TestQuestion;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.util.List;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionsDao dao;
    private final InputOutputService ioService = new InputOutputServiceImpl(System.in, System.out);

    @Override
    public void printQuestions() throws GetTestQuestionException {
        List<TestQuestion> questions = dao.getTestQuestions();

        int i = 1;
        for (TestQuestion question : questions) {
            ioService.put("Question " + i + ":");
            ioService.put(question.getQuestion());
            ioService.put("");
            ioService.put("Choose the answer(s):");
            for (int j = 0; j < question.getAnswers().size(); j++) {
                ioService.put((char) (65 + j) +". " + question.getAnswers().get(j).getAnswer());
            }
            ioService.put("");
            i++;
        }
    }
}
