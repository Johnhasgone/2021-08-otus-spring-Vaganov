package ru.otus.spring15homework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Correction {
    private List<String> addModifications = new ArrayList<>();
    private List<String> deleteModifications = new ArrayList<>();
}
