package ru.otus.spring_homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring_homework.dao.QuestionsDao;
import ru.otus.spring_homework.domain.TestQuestion;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionsDao dao;
    private final InputOutputService ioService;

    public void startTest() {
        String fullName = null;
        ioService.put("Hello! Enter your name and surname: ");

        while (fullName == null || fullName.equals("")) {
            fullName = ioService.get();
            if (fullName == null || fullName.equals("")) {
                ioService.put("Please, enter your correct name and surname:");
            } else {
                ioService.put("Start your test!\n");
            }
        }
    }

    public boolean getAnswer(TestQuestion question) {
        ioService.put("");
        ioService.put("Enter a letter of the answer: ");
        String letter = null;
        while (letter == null
                || letter.length() != 1
                || letter.toUpperCase().charAt(0) < 65
                || letter.toUpperCase().charAt(0) > 68) {
            letter = ioService.get();
            if (letter == null
                    || letter.length() != 1
                    || letter.toUpperCase().charAt(0) < 65
                    || letter.toUpperCase().charAt(0) > 68) {
                ioService.put("Enter only a letter of the answer from A to D:");
            }
        }
        return question.getAnswers()
                .get(letter.toUpperCase().charAt(0) - 65)
                .isCorrect();
    }

    public void checkTestPassed(int score) {
        if (score < dao.getMinAnswers()) {
            ioService.put("Your score is " + score + " correct answer(s). Please, try again.");
        } else {
            ioService.put("Congratulations! You passed the test!");
        }
    }

    @Override
    public void performTest() {
        startTest();

        List<TestQuestion> questions;
        try {
            questions = dao.getTestQuestions();
        } catch (GetTestQuestionException e) {
            ioService.put("Can't get questions for your test. Please contact to your administrator");
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        int i = 1;
        int score = 0;
        for (TestQuestion question : questions) {
            ioService.put("Question " + i + ":");
            ioService.put(question.getQuestion());
            ioService.put("");
            ioService.put("Choose the answer:");
            for (int j = 0; j < question.getAnswers().size(); j++) {
                ioService.put((char) (65 + j) +". " + question.getAnswers().get(j).getAnswer());
            }
            if (getAnswer(question)) {
                score++;
            }
            i++;
        }
        checkTestPassed(score);
    }
}
