package ru.otus.spring_homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import ru.otus.spring_homework.config.AppProps;
import ru.otus.spring_homework.dao.QuestionsDaoCsv;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class QuestionServiceImplTest {
    @Mock
    private QuestionsDaoCsv dao;
    @Mock
    private AppProps props;
    @Mock
    private InputOutputServiceImpl ioService;
    @Mock
    private MessageSource messageSource;

    @Test
    @DisplayName("should invoke getTestQuestions() on dao")
    public void shouldInvokeGetTestQuestions() throws GetTestQuestionException {
        when(ioService.readLine()).thenReturn("A");
        when(props.getMinAnswers()).thenReturn(3);
        when(props.getLocale()).thenReturn("ru-RU");

        QuestionServiceImpl service = new QuestionServiceImpl(dao, ioService, props, messageSource);

        service.performTest();
        verify(dao).getTestQuestions();
    }
}