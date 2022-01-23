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
    public String startProcess(int count) {

        for (int i = 0; i < count; i++) {
            Law law = process.process(department.createDraftLaw());
            System.out.println(law.getTitle() + "\n\n" + law.getText());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return  "ok";
    }
}