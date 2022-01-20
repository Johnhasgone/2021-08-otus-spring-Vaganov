package ru.otus.spring15homework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DraftLaw {
    private String title;
    private String text;
    private List<Modification> modifications;
}
