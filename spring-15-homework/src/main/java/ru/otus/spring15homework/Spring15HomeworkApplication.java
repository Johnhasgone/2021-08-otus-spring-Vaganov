package ru.otus.spring15homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.spring15homework.config.LegislativeProcess;
import ru.otus.spring15homework.service.InitiativeDepartment;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@SpringBootApplication
public class Spring15HomeworkApplication {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(Spring15HomeworkApplication.class, args);

        LegislativeProcess process = ctx.getBean( LegislativeProcess.class );

        InitiativeDepartment dep = ctx.getBean(InitiativeDepartment.class);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        process.process( dep.createDraftLaw() );


    }


}
