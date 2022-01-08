package ru.otus.spring14homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class BatchCommands {
    private final Job libraryConvertJob;
    private final JobLauncher jobLauncher;


    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startLibraryMigrationJob() throws Exception {
        JobExecution execution = jobLauncher.run(libraryConvertJob, new JobParameters());
        System.out.println(execution);
    }
}
