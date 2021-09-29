package ru.otus.spring_homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring_homework.config.AppProps;
import ru.otus.spring_homework.dao.QuestionsDaoCsv;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class QuestionServiceImplTest {
    @MockBean
    private QuestionsDaoCsv dao;
    @MockBean
    private AppProps props;
    @MockBean
    private IOLocalizationServiceImpl ioService;


    @Autowired
    private QuestionServiceImpl questionService;

    @Test
    @DisplayName("should invoke getTestQuestions() on dao")
    public void shouldInvokeGetTestQuestions() throws GetTestQuestionException {
        when(ioService.readLine()).thenReturn("A");
        when(props.getMinAnswers()).thenReturn(3);

        questionService.performTest();
        verify(dao).getTestQuestions();
    }
}