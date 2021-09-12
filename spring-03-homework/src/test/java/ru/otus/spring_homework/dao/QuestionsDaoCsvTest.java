package ru.otus.spring_homework.dao;

import com.opencsv.CSVReader;
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
    @Mock
    private CSVReader csvReader;


    @Test
    @DisplayName("should invoke getInputStream() on dao")
    public void shouldInvokeTestQuestions() throws IOException, GetTestQuestionException {
        when(testQuestions.getInputStream()).thenReturn(inputStream);
        when(props.getResources()).thenReturn(resources);
        when(resources.getRu()).thenReturn(testQuestions);
        when(props.getLocale()).thenReturn("ru-RU");

        QuestionsDaoCsv daoCsv = new QuestionsDaoCsv(props);

        daoCsv.getTestQuestions();
        verify(testQuestions).getInputStream();
    }

    @Test
    @DisplayName("should throw GetTestQuestionException")
    public void shouldThrowGetTestQuestionException() throws IOException {
        when(testQuestions.getInputStream()).thenThrow(new IOException());
        when(props.getResources()).thenReturn(resources);
        when(props.getLocale()).thenReturn("ru-RU");
        when(resources.getRu()).thenReturn(testQuestions);


        QuestionsDaoCsv daoCsv = new QuestionsDaoCsv(props);

        assertThrows(GetTestQuestionException.class, daoCsv::getTestQuestions);
    }

    @Test
    @DisplayName("should return list of TestQuestion")
    public void shouldReturnListOfTestQuestion() throws GetTestQuestionException, IOException {
        when(props.getResources()).thenReturn(resources);
        when(props.getLocale()).thenReturn("ru-RU");
        when(resources.getRu()).thenReturn(testQuestions);
        when(testQuestions.getInputStream()).thenReturn(inputStream);
        // TODO get power mock for mocking creating local instances

        QuestionsDaoCsv daoCsv = new QuestionsDaoCsv(props);
        daoCsv.getTestQuestions();
    }
}