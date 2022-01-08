package ru.otus.spring14homework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import ru.otus.spring14homework.dao.no_sql.AuthorMongoRepository;
import ru.otus.spring14homework.dao.sql.AuthorRepository;
import ru.otus.spring14homework.domain.no_sql.Author;
import ru.otus.spring14homework.service.AuthorConvertService;

import java.util.HashMap;
import java.util.List;

@Configuration
public class JobConfig {
    private final Logger logger = LoggerFactory.getLogger("Batch");
    private static final int CHUNK_SIZE = 5;
    public static final String LIBRARY_CONVERSION_JOB_NAME = "libraryConversionJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorConvertService authorConvertService;

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
    public ItemProcessor<ru.otus.spring14homework.domain.sql.Author, Author> processor() {
        return authorConvertService::convert;
    }

    @Bean
    public MongoItemWriter<Author> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Author>()
                .template(mongoTemplate)
                .collection("Author")
                .build();
    }

    @Bean
    public Job libraryConversionJob(Step convertAuthorStep) {
        return jobBuilderFactory.get(LIBRARY_CONVERSION_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(convertAuthorStep)
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
    public Step convertAuthorStep(ItemReader<ru.otus.spring14homework.domain.sql.Author> authorReader, ItemWriter<Author> authorWriter,
                                  ItemProcessor<ru.otus.spring14homework.domain.sql.Author, Author> itemProcessor) {
        return stepBuilderFactory.get("convertAuthorStep")
                .<ru.otus.spring14homework.domain.sql.Author, Author>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .processor(itemProcessor)
                .writer(authorWriter)
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
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
//                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
}
