package ru.otus.spring_homework.dao;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface QuestionsDao {
    List<String[]> getTestQuestions() throws IOException, CsvException;
}
