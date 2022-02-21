package ru.otus.spring_homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring_homework.dao.QuestionsDaoCsv;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;


@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    @Mock
    private QuestionsDaoCsv dao;
    @InjectMocks
    private QuestionServiceImpl service;

    @Test
    @DisplayName("should invoke getTestQuestions() on dao")
    public void shouldInvokeGetTestQuestions() throws GetTestQuestionException {
        service.printQuestions();
            verify(dao).getTestQuestions();


        int i1 = Integer.MAX_VALUE;
        int i2 = Integer.MAX_VALUE;
        System.out.println(i1 + i2);

        int k = 0;
        for (int i = 0; i < 10; i++) {
            k = k++;
        }
        System.out.println(k);

        List<String> numbers = new ArrayList(Arrays.asList("first", "second", "third"));
        for (String number : numbers) {
            if ("third".equals(number)) {
                numbers.add("fourth");
            }
        }
        System.out.println(numbers.size());
    }

}