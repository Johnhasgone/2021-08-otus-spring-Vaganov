package ru.otus.spring_homework.service;

import org.springframework.lang.NonNull;

public interface InputOutputService {
    @NonNull
    String readLine();
    void output(String string);
}
