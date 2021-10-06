package ru.otus.spring_homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @MockBean
    private AppProps props;
    @Mock
    private Resource testQuestions;
    @MockBean
    private InputStream inputStream;

    @Autowired
    private QuestionsDaoCsv daoCsv;

    @Test
    @DisplayName("should invoke getInputStream() on dao")
    public void shouldInvokeTestQuestions() throws IOException, GetTestQuestionException {
        when(props.getResource()).thenReturn(testQuestions);
        when(testQuestions.getInputStream()).thenReturn(inputStream);
        daoCsv.getTestQuestions();
        verify(testQuestions).getInputStream();
    }

    @Test
    @DisplayName("should throw GetTestQuestionException")
    public void shouldThrowGetTestQuestionException() throws IOException {
        when(props.getResource()).thenReturn(testQuestions);
        when(testQuestions.getInputStream()).thenThrow(new IOException());
        assertThrows(GetTestQuestionException.class, daoCsv::getTestQuestions);
    }
}