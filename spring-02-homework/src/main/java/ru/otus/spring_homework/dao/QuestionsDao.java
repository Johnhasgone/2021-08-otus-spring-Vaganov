package ru.otus.spring_homework.dao;

import ru.otus.spring_homework.domain.TestQuestion;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.util.List;

public interface QuestionsDao {
    List<TestQuestion> getTestQuestions() throws GetTestQuestionException;
}
