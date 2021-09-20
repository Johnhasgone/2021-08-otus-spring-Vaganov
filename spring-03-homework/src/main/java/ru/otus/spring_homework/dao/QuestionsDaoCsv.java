package ru.otus.spring_homework.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.spring_homework.config.QuestionsDaoProps;
import ru.otus.spring_homework.domain.Answer;
import ru.otus.spring_homework.domain.TestQuestion;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class QuestionsDaoCsv implements QuestionsDao {

    private final Resource testQuestions;
    private static final String SEPARATOR = "~\\|~";

    @Autowired
    public QuestionsDaoCsv(QuestionsDaoProps props) {
        this.testQuestions = props.getResource();
    }

    @Override
    public List<TestQuestion> getTestQuestions() throws GetTestQuestionException {

        List<String[]> csvResult;
        List<TestQuestion> questions = new ArrayList<>();
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(testQuestions.getInputStream()))
                .withCSVParser(csvParser)
                .build()) {

            csvResult = csvReader.readAll();
        } catch (CsvException | IOException e) {
            throw new GetTestQuestionException("Can't get test questions: ", e);
        }

        for (String[] row : csvResult) {
            TestQuestion question = new TestQuestion();
            question.setQuestion(row[0]);
            question.setAnswers(Arrays.stream(row)
                    .skip(1)
                    .map(e -> new Answer(e.split(SEPARATOR)[0],
                            Boolean.parseBoolean(e.split(SEPARATOR)[1])))
                    .collect(Collectors.toList()));

            questions.add(question);
        }
        return questions;
    }
}
