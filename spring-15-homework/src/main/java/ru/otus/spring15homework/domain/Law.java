package ru.otus.spring15homework.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Law {
    private String text;
    private String title;
    private String registerNumber;
    private LocalDate signedDate;
}
