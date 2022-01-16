package ru.otus.spring14homework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import ru.otus.spring14homework.dao.sql.SqlDbAuthorRepository;
import ru.otus.spring14homework.dao.sql.SqlDbBookRepository;
import ru.otus.spring14homework.dao.sql.SqlDbGenreRepository;
import ru.otus.spring14homework.domain.no_sql.MongoAuthor;
import ru.otus.spring14homework.domain.no_sql.MongoBook;
import ru.otus.spring14homework.domain.no_sql.MongoGenre;
import ru.otus.spring14homework.domain.sql.SqlDbAuthor;
import ru.otus.spring14homework.domain.sql.SqlDbBook;
import ru.otus.spring14homework.domain.sql.SqlDbGenre;
import ru.otus.spring14homework.service.AuthorConvertService;
import ru.otus.spring14homework.service.BookConvertService;
import ru.otus.spring14homework.service.GenreConvertService;
import ru.otus.spring14homework.service.listener.MigrationProcessListener;
import ru.otus.spring14homework.service.listener.MigrationReadListener;
import ru.otus.spring14homework.service.listener.MigrationWriteListener;

import java.util.*;

@Configuration
public class JobConfig {
    private final Logger logger = LoggerFactory.getLogger("Batch");
    private static final int CHUNK_SIZE = 5;
    public static final String LIBRARY_MIGRATION_JOB_NAME = "libraryMigrationJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SqlDbAuthorRepository authorRepository;

    @Autowired
    private SqlDbGenreRepository genreRepository;

    @Autowired
    private SqlDbBookRepository bookRepository;

    @Autowired
    private AuthorConvertService authorConvertService;

    @Autowired
    private GenreConvertService genreConvertService;

    @Autowired
    private BookConvertService bookConvertService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public RepositoryItemReader<SqlDbAuthor> authorReader() {
        return new RepositoryItemReaderBuilder<SqlDbAuthor>()
                .name("authorReader")
                .repository(authorRepository)
                .pageSize(CHUNK_SIZE)
                .sorts(new HashMap<>())
                .methodName("findAll")
                .build();
    }

    @Bean
    public RepositoryItemReader<SqlDbGenre> genreReader() {
        return new RepositoryItemReaderBuilder<SqlDbGenre>()
                .name("genreReader")
                .repository(genreRepository)
                .pageSize(CHUNK_SIZE)
                .sorts(new HashMap<>())
                .methodName("findAll")
                .build();
    }

    @Bean
    public RepositoryItemReader<SqlDbBook> bookReader() {
        return new RepositoryItemReaderBuilder<SqlDbBook>()
                .name("bookReader")
                .repository(bookRepository)
                .pageSize(CHUNK_SIZE)
                .sorts(new HashMap<>())
                .methodName("findAll")
                .build();
    }

    @Bean
    public ItemProcessor<SqlDbAuthor, MongoAuthor> authorProcessor() {
        return authorConvertService::convert;
    }

    @Bean
    public ItemProcessor<SqlDbGenre, MongoGenre> genreProcessor() {
        return genreConvertService::convert;
    }

    @Bean ItemProcessor<SqlDbBook, MongoBook> bookProcessor() {
        return bookConvertService::convert;
    }

    @Bean
    public MongoItemWriter<MongoAuthor> authorWriter() {
        return new MongoItemWriterBuilder<MongoAuthor>()
                .template(mongoTemplate)
                .collection("author")
                .build();
    }

    @Bean
    public MongoItemWriter<MongoGenre> genreWriter() {
        return new MongoItemWriterBuilder<MongoGenre>()
                .template(mongoTemplate)
                .collection("genre")
                .build();
    }

    @Bean
    public MongoItemWriter<MongoBook> bookWriter() {
        return new MongoItemWriterBuilder<MongoBook>()
                .template(mongoTemplate)
                .collection("book")
                .build();
    }

    @Bean
    public Job libraryMigrationJob(Step convertAuthorStep, Step convertGenreStep, Step convertBookStep) {
        return jobBuilderFactory.get(LIBRARY_MIGRATION_JOB_NAME)
                .start(splitFlow(convertAuthorStep, convertGenreStep))
                .next(convertBookStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Flow splitFlow(Step convertAuthorStep, Step convertGenreStep) {
        return new FlowBuilder<SimpleFlow>("authorAndGenreSplitFlow")
                .split(taskExecutor())
                .add(convertAuthorFlow(convertAuthorStep), convertGenreFlow(convertGenreStep))
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

    @Bean
    public Flow convertAuthorFlow(Step convertAuthorStep) {
        return new FlowBuilder<SimpleFlow>("convertAuthorFlow")
                .start(convertAuthorStep)
                .build();
    }

    @Bean
    public Flow convertGenreFlow(Step convertGenreStep) {
        return new FlowBuilder<SimpleFlow>("convertGenreFlow")
                .start(convertGenreStep)
                .build();
    }

    @Bean
    public Step convertAuthorStep() {
        return stepBuilderFactory.get("convertAuthorStep")
                .allowStartIfComplete(true)
                .<SqlDbAuthor, MongoAuthor>chunk(CHUNK_SIZE)
                .reader(authorReader())
                .processor(authorProcessor())
                .writer(authorWriter())
                .listener(new MigrationReadListener<>())
                .listener(new MigrationWriteListener<>())
                .listener(new MigrationProcessListener<>())
                .build();
    }

    @Bean
    public Step convertGenreStep() {
        return stepBuilderFactory.get("convertGenreStep")
                .allowStartIfComplete(true)
                .<SqlDbGenre, MongoGenre>chunk(CHUNK_SIZE)
                .reader(genreReader())
                .processor(genreProcessor())
                .writer(genreWriter())
                .listener(new MigrationReadListener<>())
                .listener(new MigrationWriteListener<>())
                .listener(new MigrationProcessListener<>())
                .build();
    }

    @Bean
    public Step convertBookStep() {
        return stepBuilderFactory.get("convertBookStep")
                .allowStartIfComplete(true)
                .<SqlDbBook, MongoBook>chunk(CHUNK_SIZE)
                .reader(bookReader())
                .processor(bookProcessor())
                .writer(bookWriter())
                .listener(new MigrationReadListener<>())
                .listener(new MigrationWriteListener<>())
                .listener(new MigrationProcessListener<>())
                .build();
    }
}
