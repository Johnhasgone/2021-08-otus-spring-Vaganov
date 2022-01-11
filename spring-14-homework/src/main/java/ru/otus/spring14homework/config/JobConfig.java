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
import ru.otus.spring14homework.dao.AuthorRepository;
import ru.otus.spring14homework.dao.BookRepository;
import ru.otus.spring14homework.dao.GenreRepository;
import ru.otus.spring14homework.domain.no_sql.Author;
import ru.otus.spring14homework.domain.no_sql.Book;
import ru.otus.spring14homework.domain.no_sql.Genre;
import ru.otus.spring14homework.service.AuthorConvertService;
import ru.otus.spring14homework.service.BookConvertService;
import ru.otus.spring14homework.service.GenreConvertService;
import ru.otus.spring14homework.service.listener.MigrationProcessListener;
import ru.otus.spring14homework.service.listener.MigrationReadListener;
import ru.otus.spring14homework.service.listener.MigrationWriteListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfig {
    private final Logger logger = LoggerFactory.getLogger("Batch");
    private static final int CHUNK_SIZE = 5;
    public static final String LIBRARY_MIGRATION_JOB_NAME = "libraryMigrationJob";
    public Map<Long, String> authorIdMap = new HashMap<>();
    public Map<Long, String> genreIdMap = new HashMap<>();
    public Map<Long, String> bookIdMap = new HashMap<>();

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public AuthorConvertService authorConvertService() {
        return new AuthorConvertService(authorIdMap);
    }

    @Bean
    public GenreConvertService genreConvertService() {
        return new GenreConvertService(genreIdMap);
    }

    @Bean
    public BookConvertService bookConvertService() {
        return new BookConvertService(authorIdMap, genreIdMap, bookIdMap);
    }

    @Bean
    public RepositoryItemReader<ru.otus.spring14homework.domain.sql.Author> authorReader(AuthorRepository authorRepository) {
        return new RepositoryItemReaderBuilder<ru.otus.spring14homework.domain.sql.Author>()
                .name("authorReader")
                .repository(authorRepository)
                .pageSize(CHUNK_SIZE)
                .sorts(new HashMap<>())
                .methodName("findAll")
                .build();
    }

    @Bean
    public RepositoryItemReader<ru.otus.spring14homework.domain.sql.Genre> genreReader(GenreRepository genreRepository) {
        return new RepositoryItemReaderBuilder<ru.otus.spring14homework.domain.sql.Genre>()
                .name("genreReader")
                .repository(genreRepository)
                .pageSize(CHUNK_SIZE)
                .sorts(new HashMap<>())
                .methodName("findAll")
                .build();
    }

    @Bean
    public RepositoryItemReader<ru.otus.spring14homework.domain.sql.Book> bookReader(BookRepository bookRepository) {
        return new RepositoryItemReaderBuilder<ru.otus.spring14homework.domain.sql.Book>()
                .name("bookReader")
                .repository(bookRepository)
                .pageSize(CHUNK_SIZE)
                .sorts(new HashMap<>())
                .methodName("findAll")
                .build();
    }

    @Bean
    public ItemProcessor<ru.otus.spring14homework.domain.sql.Author, Author> authorProcessor() {
        return authorConvertService()::convert;
    }

    @Bean
    public ItemProcessor<ru.otus.spring14homework.domain.sql.Genre, Genre> genreProcessor() {
        return genreConvertService()::convert;
    }

    @Bean ItemProcessor<ru.otus.spring14homework.domain.sql.Book, Book> bookProcessor() {
        return bookConvertService()::convert;
    }

    @Bean
    public MongoItemWriter<Author> authorWriter() {
        return new MongoItemWriterBuilder<Author>()
                .template(mongoTemplate)
                .collection("author")
                .build();
    }

    @Bean
    public MongoItemWriter<Genre> genreWriter() {
        return new MongoItemWriterBuilder<Genre>()
                .template(mongoTemplate)
                .collection("genre")
                .build();
    }

    @Bean
    public MongoItemWriter<Book> bookWriter() {
        return new MongoItemWriterBuilder<Book>()
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
                .<ru.otus.spring14homework.domain.sql.Author, Author>chunk(CHUNK_SIZE)
                .reader(authorReader(authorRepository))
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
                .<ru.otus.spring14homework.domain.sql.Genre, Genre>chunk(CHUNK_SIZE)
                .reader(genreReader(genreRepository))
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
                .<ru.otus.spring14homework.domain.sql.Book, Book>chunk(CHUNK_SIZE)
                .reader(bookReader(bookRepository))
                .processor(bookProcessor())
                .writer(bookWriter())
                .listener(new MigrationReadListener<>())
                .listener(new MigrationWriteListener<>())
                .listener(new MigrationProcessListener<>())
                .build();
    }
}
