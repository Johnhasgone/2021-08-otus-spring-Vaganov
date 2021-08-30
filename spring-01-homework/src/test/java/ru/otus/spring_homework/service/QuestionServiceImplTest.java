package ru.otus.spring_homework.service;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring_homework.dao.QuestionsDaoImpl;

import java.io.IOException;

import static org.mockito.Mockito.verify;


@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    @Mock
    private QuestionsDaoImpl dao;
    @InjectMocks
    private QuestionServiceImpl service;

    @Test
    @DisplayName("should envoke getTestQuestions() on dao")
    public void shouldEnvokeGetTestQuestions() throws IOException, CsvException {
        service.printQuestions();
        verify(dao).getTestQuestions();
    }

}