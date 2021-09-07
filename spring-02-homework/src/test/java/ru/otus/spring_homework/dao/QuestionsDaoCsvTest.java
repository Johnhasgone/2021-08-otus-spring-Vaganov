package ru.otus.spring_homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionsDaoCsvTest {
    @Mock
    private Resource testQuestions;
    @InjectMocks
    private QuestionsDaoCsv daoCsv;


    @Test
    @DisplayName("should invoke getTestQuestions() on dao")
    public void shouldInvokeTestQuestions() throws IOException, GetTestQuestionException {
        when(testQuestions.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[1]));
        daoCsv.getTestQuestions();
        verify(testQuestions).getInputStream();
    }

    @Test
    @DisplayName("should throw GetTestQuestionException")
    public void shouldThrowGetTestQuestionException() throws IOException {
        when(testQuestions.getInputStream()).thenThrow(new IOException());
        assertThrows(GetTestQuestionException.class, () -> daoCsv.getTestQuestions());
    }
}