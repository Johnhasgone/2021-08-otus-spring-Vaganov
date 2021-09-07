package ru.otus.spring_homework.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class InputOutputServiceImpl implements InputOutputService {
    private InputStream inputStream = System.in;
    private PrintStream printStream = System.out;

    @Override
    public String get() {
        Scanner scanner = new Scanner(inputStream);
        return scanner.nextLine();
    }

    @Override
    public void put(String string) {
        printStream.println(string);
    }
}
