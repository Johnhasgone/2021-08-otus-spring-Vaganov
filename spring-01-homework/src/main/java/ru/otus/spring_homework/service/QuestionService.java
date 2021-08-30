package ru.otus.spring_homework.service;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public interface QuestionService {
    void printQuestions() throws CsvException, IOException;
}
