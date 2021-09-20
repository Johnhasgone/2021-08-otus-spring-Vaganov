package ru.otus.spring_homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring_homework.config.QuestionServiceProps;
import ru.otus.spring_homework.dao.QuestionsDao;
import ru.otus.spring_homework.domain.TestQuestion;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionsDao dao;
    private final IOLocalizationServiceImpl ioService;
    private final QuestionServiceProps config;

    private static final char FIRST_BULLET = 'A';


    private void startTest() {
        String fullName;
        ioService.printLocalized("strings.greeting");
        fullName = ioService.readLine();
        ioService.printLocalized("strings.start", fullName);
    }

    private boolean getAnswer(TestQuestion question) {
        int answersCount = question.getAnswers().size();

        ioService.printLocalized("strings.get-answer");
        String letter = ioService.readLine();
        while (letter.length() != 1
                || letter.toUpperCase().charAt(0) < FIRST_BULLET
                || letter.toUpperCase().charAt(0) >= FIRST_BULLET + answersCount) {
            ioService.printLocalized("strings.get-answer-mistake",
                    FIRST_BULLET, (char) (FIRST_BULLET + answersCount - 1));
            letter = ioService.readLine();
        }
        return question.getAnswers()
                .get(letter.toUpperCase().charAt(0) - FIRST_BULLET)
                .isCorrect();
    }

    private void checkTestPassed(int score) {
        if (score < config.getMinAnswers()) {
            ioService.printLocalized("strings.test-failure", score);
        } else {
            ioService.printLocalized("strings.test-passed");
        }
    }

    @Override
    public void performTest() {
        startTest();

        List<TestQuestion> questions;
        try {
            questions = dao.getTestQuestions();
        } catch (GetTestQuestionException e) {
            ioService.printLocalized("strings.get-questions-failure");
            return;
        }

        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            TestQuestion question = questions.get(i);
            printQuestionsWithAnswers(question, i);
            if (getAnswer(question)) {
                score++;
            }
        }
        checkTestPassed(score);
    }

    private void printQuestionsWithAnswers(TestQuestion question, int questionNumber) {
        ioService.printLocalized("strings.question", questionNumber + 1);
        ioService.print(question.getQuestion());
        ioService.print("");
        ioService.printLocalized("strings.answer");
        for (int j = 0; j < question.getAnswers().size(); j++) {
            ioService.print((char) (FIRST_BULLET + j) +". " + question.getAnswers().get(j).getAnswer());
        }
    }
}
