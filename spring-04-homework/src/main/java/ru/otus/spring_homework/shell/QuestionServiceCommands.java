package ru.otus.spring_homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring_homework.service.QuestionService;

@ShellComponent
@RequiredArgsConstructor
public class QuestionServiceCommands {
    private final QuestionService questionService;
    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "guest") String name) {
        userName = name;
        return "Welcome, " + name;
    }

    @ShellMethod(value = "Start test", key = {"start"})
    @ShellMethodAvailability(value = "isTestAvailable")
    public String startTest() {
        questionService.performTest();
        return "The test is over. Enter \"exit\" to finish or \"start\" to pass the test again";
    }

    private Availability isTestAvailable() {
        return userName == null
                ? Availability.unavailable("Please, login to start the test")
                : Availability.available();
    }
}
