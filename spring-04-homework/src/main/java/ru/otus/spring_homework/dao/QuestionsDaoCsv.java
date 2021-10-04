package ru.otus.spring_homework.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.spring_homework.config.QuestionsDaoProps;
import ru.otus.spring_homework.domain.Answer;
import ru.otus.spring_homework.domain.TestQuestion;
import ru.otus.spring_homework.exceptions.GetTestQuestionException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Repository
public class QuestionsDaoCsv implements QuestionsDao {

    private final QuestionsDaoProps props;
    private static final String SEPARATOR = "~\\|~";

    @Autowired
    public QuestionsDaoCsv(QuestionsDaoProps props) {
        this.props = props;
    }

    @Override
    public List<TestQuestion> getTestQuestions() throws GetTestQuestionException {

        List<String[]> csvResult;
        List<TestQuestion> questions = new ArrayList<>();
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader csvReader = new CSVReaderBuilder(
                new InputStreamReader(props.getResource().getInputStream())
        )
                .withCSVParser(csvParser)
                .build()) {

            csvResult = csvReader.readAll();
        } catch (CsvException | IOException e) {
            throw new GetTestQuestionException("Can't get test questions: ", e);
        }

        for (String[] row : csvResult) {
            if (row.length < 3) {
                throw new GetTestQuestionException("Got incorrect question format");
            }
            TestQuestion question = new TestQuestion();
            question.setQuestion(row[0]);

            List<Answer> answers = new ArrayList<>();
            for (int i = 1; i < row.length; i++) {
                String[] answer = row[i].split(SEPARATOR);
                if (answer.length != 2) {
                    throw new GetTestQuestionException("Got incorrect answer format");
                }
                answers.add(new Answer(answer[0], Boolean.parseBoolean(answer[1])));
            }

            question.setAnswers(answers);
            questions.add(question);
        }
        return questions;
    }
}
