package ru.otus.spring15homework.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraftLaw {
    private String title;
    private String text;
    private Correction correction;
}
