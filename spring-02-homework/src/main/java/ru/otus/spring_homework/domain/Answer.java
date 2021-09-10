package ru.otus.spring_homework.domain;

import lombok.Data;

@Data
public class Answer {
    private String answer;
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
}
