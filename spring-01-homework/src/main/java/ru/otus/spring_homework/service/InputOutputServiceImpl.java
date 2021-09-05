package ru.otus.spring_homework.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@AllArgsConstructor
@Data
public class InputOutputServiceImpl implements InputOutputService {
    private final InputStream inputStream;
    private final PrintStream printStream;

    @Override
    public String get() {
        Scanner scanner = new Scanner(inputStream);
        return scanner.next();
    }

    @Override
    public void put(String string) {
        printStream.println(string);
    }
}
