package ru.otus.spring_homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring_homework.dao.QuestionsDaoCsv;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    @Mock
    private QuestionsDaoCsv dao;

    @Mock
    private InputOutputServiceImpl ioService;

    @InjectMocks
    private QuestionServiceImpl service;

    @Test
    @DisplayName("should invoke getTestQuestions() on dao")
    public void shouldInvokeGetTestQuestions() throws GetTestQuestionException {
        when(ioService.get()).thenReturn("A");
        service.performTest();
        verify(dao).getTestQuestions();
    }
}