package ru.otus.spring_homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {
    private final PrintStream printStream;
    private final Scanner scanner;

    @Autowired
    public InputOutputServiceImpl(@Value("#{T(System).in}")InputStream inputStream,
                                  @Value("#{T(System).out}") PrintStream printStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String readLine() {
        String input = null;
        while (input == null || "".equals(input)) {
            input = scanner.nextLine();
        }
        return input;
    }

    @Override
    public void output(String string) {
        printStream.println(string);
    }
}
