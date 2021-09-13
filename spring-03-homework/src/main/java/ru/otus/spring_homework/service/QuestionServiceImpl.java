package ru.otus.spring_homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring_homework.config.AppProps;
import ru.otus.spring_homework.dao.QuestionsDao;
import ru.otus.spring_homework.domain.TestQuestion;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.util.List;
import java.util.Locale;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionsDao dao;
    private final InputOutputService ioService;
    private final int minAnswers;
    private final Locale locale;
    private final MessageSource msg;

    private static final char FIRST_BULLET = 'A';

    @Autowired
    public QuestionServiceImpl(QuestionsDao dao,
                               InputOutputService ioService,
                               AppProps props,
                               MessageSource messageSource) {
        this.dao = dao;
        this.ioService = ioService;
        this.minAnswers = props.getMinAnswers();
        this.locale = Locale.forLanguageTag(props.getLocale());
        this.msg = messageSource;

    }

    private void startTest() {
        String fullName;
        ioService.output(localized("strings.greeting", null));
        fullName = ioService.readLine();
        ioService.output(localized("strings.start", new String[] {fullName}));
    }

    private boolean getAnswer(TestQuestion question) {
        int answersCount = question.getAnswers().size();

        ioService.output(localized("strings.get-answer", null));
        String letter = ioService.readLine();
        while (letter.length() != 1
                || letter.toUpperCase().charAt(0) < FIRST_BULLET
                || letter.toUpperCase().charAt(0) >= FIRST_BULLET + answersCount) {
            ioService.output(localized("strings.get-answer-mistake",
                    new Character[]{FIRST_BULLET, (char) (FIRST_BULLET + answersCount - 1)}));
            letter = ioService.readLine();
        }
        return question.getAnswers()
                .get(letter.toUpperCase().charAt(0) - FIRST_BULLET)
                .isCorrect();
    }

    private void checkTestPassed(int score) {
        if (score < minAnswers) {
            ioService.output(localized("strings.test-failure", new Integer[] {score}));
        } else {
            ioService.output(localized("strings.test-passed", null));
        }
    }

    @Override
    public void performTest() {
        startTest();

        List<TestQuestion> questions;
        try {
            questions = dao.getTestQuestions();
        } catch (GetTestQuestionException e) {
            ioService.output(localized("strings.get-questions-failure", null));
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
        ioService.output(localized("strings.question", new Integer[] {questionNumber + 1}));
        ioService.output(question.getQuestion());
        ioService.output("");
        ioService.output(localized("strings.answer", null));
        for (int j = 0; j < question.getAnswers().size(); j++) {
            ioService.output((char) (FIRST_BULLET + j) +". " + question.getAnswers().get(j).getAnswer());
        }
    }

    private String localized(String message, Object[] objects) {
        return msg.getMessage(message, objects, locale);
    }
}
