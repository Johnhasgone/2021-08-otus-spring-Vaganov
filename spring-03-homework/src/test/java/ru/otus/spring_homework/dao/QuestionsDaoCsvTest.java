package ru.otus.spring_homework.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.otus.spring_homework.config.AppProps;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class QuestionsDaoCsvTest {
    @Mock
    private Resource testQuestions;
    @Mock
    private AppProps.Resources resources;
    @Mock
    private AppProps props;
    @Mock
    private InputStream inputStream;


    @BeforeEach
    private void setMockInvocationResult() {
        when(props.getResources()).thenReturn(resources);
        when(props.getLocale()).thenReturn("ru-RU");
        when(resources.getRu()).thenReturn(testQuestions);
    }


    @Test
    @DisplayName("should invoke getInputStream() on dao")
    public void shouldInvokeTestQuestions() throws IOException, GetTestQuestionException {
        when(testQuestions.getInputStream()).thenReturn(inputStream);
        QuestionsDaoCsv daoCsv = new QuestionsDaoCsv(props);
        daoCsv.getTestQuestions();
        verify(testQuestions).getInputStream();
    }

    @Test
    @DisplayName("should throw GetTestQuestionException")
    public void shouldThrowGetTestQuestionException() throws IOException {
        when(testQuestions.getInputStream()).thenThrow(new IOException());
        QuestionsDaoCsv daoCsv = new QuestionsDaoCsv(props);
        assertThrows(GetTestQuestionException.class, daoCsv::getTestQuestions);
    }
}