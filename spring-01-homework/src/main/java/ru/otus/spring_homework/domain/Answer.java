package ru.otus.spring_homework.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Answer {
    private String answer;
    private boolean isCorrect;
    @Getter
    @Setter
    private static String separator = "~|~";

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
}
