package ru.otus.spring15homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring15homework.config.LegislativeProcess;
import ru.otus.spring15homework.domain.Law;

@ShellComponent
@RequiredArgsConstructor
public class LegislativeProcessCommands {
    private final LegislativeProcess process;
    private final InitiativeDepartment department;

    @ShellMethod(value = "start process", key = {"start"})
    public String startProcess() {
        Law law = process.process(department.createDraftLaw());

        return  law.getTitle() + "\n\n" + law.getText();
    }
}