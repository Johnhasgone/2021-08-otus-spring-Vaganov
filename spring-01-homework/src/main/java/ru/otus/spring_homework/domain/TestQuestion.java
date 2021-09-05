package ru.otus.spring_homework.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestQuestion {
    private String question;
    private List<Answer> answers = new ArrayList<>();

}
