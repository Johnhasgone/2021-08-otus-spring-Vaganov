package ru.otus.spring_homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring_homework.dao.QuestionsDao;
import ru.otus.spring_homework.domain.TestQuestion;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionsDao dao;
    private final InputOutputService ioService;
    private final Integer minAnswers;
    private static final char FIRST_BULLET = 'A';

    @Autowired
    public QuestionServiceImpl(QuestionsDao dao,
                               InputOutputService ioService,
                               @Value("${app.min-answers}") Integer minAnswers) {
        this.dao = dao;
        this.ioService = ioService;
        this.minAnswers = minAnswers;
    }

    private void startTest() {
        String fullName;
        ioService.output("Hello! Enter your name and surname: ");
        fullName = ioService.readLine();
        ioService.output("Start your test, " + fullName + "!\n");
    }

    private boolean getAnswer(TestQuestion question) {
        int answersCount = question.getAnswers().size();

        ioService.output("\nEnter a letter of the answer: ");
        String letter = ioService.readLine();
        while (letter.length() != 1
                || letter.toUpperCase().charAt(0) < FIRST_BULLET
                || letter.toUpperCase().charAt(0) >= FIRST_BULLET + answersCount) {
            ioService.output("Enter only a letter of the answer from " + FIRST_BULLET +
                    " to " + (char) (FIRST_BULLET + answersCount - 1) + ":");
            letter = ioService.readLine();
        }
        return question.getAnswers()
                .get(letter.toUpperCase().charAt(0) - FIRST_BULLET)
                .isCorrect();
    }

    private void checkTestPassed(int score) {
        if (score < minAnswers) {
            ioService.output("Your score is " + score + " correct answer(s). Please, try again.");
        } else {
            ioService.output("Congratulations! You passed the test!");
        }
    }

    @Override
    public void performTest() {
        startTest();

        List<TestQuestion> questions;
        try {
            questions = dao.getTestQuestions();
        } catch (GetTestQuestionException e) {
            ioService.output("Can't get questions for your test. Please contact to your administrator");
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
        ioService.output("Question " + (questionNumber + 1) + ":");
        ioService.output(question.getQuestion());
        ioService.output("");
        ioService.output("Choose the answer:");
        for (int j = 0; j < question.getAnswers().size(); j++) {
            ioService.output((char) (FIRST_BULLET + j) +". " + question.getAnswers().get(j).getAnswer());
        }
    }
}
