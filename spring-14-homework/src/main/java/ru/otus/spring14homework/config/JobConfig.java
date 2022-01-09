package ru.otus.spring14homework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
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
import ru.otus.spring14homework.dao.sql.AuthorRepository;
import ru.otus.spring14homework.dao.sql.BookRepository;
import ru.otus.spring14homework.dao.sql.GenreRepository;
import ru.otus.spring14homework.domain.no_sql.Author;
import ru.otus.spring14homework.domain.no_sql.Book;
import ru.otus.spring14homework.domain.no_sql.Genre;
import ru.otus.spring14homework.service.AuthorConvertService;
import ru.otus.spring14homework.service.BookConvertService;
import ru.otus.spring14homework.service.GenreConvertService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class JobConfig {
    private final Logger logger = LoggerFactory.getLogger("Batch");
    private static final int CHUNK_SIZE = 5;
    public static final String LIBRARY_MIGRATION_JOB_NAME = "libraryConversionJob";
    public Map<Long, String> authorIdMap = new HashMap<>();
    public Map<Long, String> genreIdMap = new HashMap<>();

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
        return new BookConvertService(authorIdMap, genreIdMap);
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
                .incrementer(new RunIdIncrementer())
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
        return new FlowBuilder<SimpleFlow>("splitFlow")
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
        return new FlowBuilder<SimpleFlow>("flow1")
                .start(convertAuthorStep)
                .build();
    }

    @Bean
    public Flow convertGenreFlow(Step convertGenreStep) {
        return new FlowBuilder<SimpleFlow>("flow2")
                .start(convertGenreStep)
                .build();
    }

    @Bean
    public Step convertAuthorStep() {
        return stepBuilderFactory.get("convertAuthorStep")
                .<ru.otus.spring14homework.domain.sql.Author, Author>chunk(CHUNK_SIZE)
                .reader(authorReader(authorRepository))
                .processor(authorProcessor())
                .writer(authorWriter())
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(@NonNull ru.otus.spring14homework.domain.sql.Author o) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(ru.otus.spring14homework.domain.sql.Author o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(@NonNull ru.otus.spring14homework.domain.sql.Author o, Author o2) {
                        logger.info("Конец обработки - {}", o2.getName());
                    }

                    public void onProcessError(@NonNull ru.otus.spring14homework.domain.sql.Author o, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .build();
    }

    @Bean
    public Step convertGenreStep() {
        return stepBuilderFactory.get("convertGenreStep")
                .<ru.otus.spring14homework.domain.sql.Genre, Genre>chunk(CHUNK_SIZE)
                .reader(genreReader(genreRepository))
                .processor(genreProcessor())
                .writer(genreWriter())
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(@NonNull ru.otus.spring14homework.domain.sql.Genre o) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(ru.otus.spring14homework.domain.sql.Genre o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(@NonNull ru.otus.spring14homework.domain.sql.Genre o, Genre o2) {
                        logger.info("Конец обработки - {}", o2.getName());
                    }

                    public void onProcessError(@NonNull ru.otus.spring14homework.domain.sql.Genre o, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .build();
    }

    @Bean
    public Step convertBookStep() {
        return stepBuilderFactory.get("convertBookStep")
                .<ru.otus.spring14homework.domain.sql.Book, Book>chunk(CHUNK_SIZE)
                .reader(bookReader(bookRepository))
                .processor(bookProcessor())
                .writer(bookWriter())
                .build();
    }
}
