package ru.otus.spring_homework.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@AllArgsConstructor
public class QuestionsDaoImpl implements QuestionsDao {

    Resource testQuestions;

    @Override
    public List<String[]> getTestQuestions() throws IOException, CsvException {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(';')
                .build();

        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(testQuestions.getInputStream()))
                .withCSVParser(csvParser)
                .build();

        return csvReader.readAll();
    }
}
