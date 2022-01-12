package ru.otus.spring14homework.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring14homework.dao.no_sql.MongoAuthorRepository;
import ru.otus.spring14homework.dao.no_sql.MongoBookRepository;
import ru.otus.spring14homework.dao.no_sql.MongoGenreRepository;
import ru.otus.spring14homework.domain.no_sql.MongoBook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.spring14homework.config.JobConfig.LIBRARY_MIGRATION_JOB_NAME;

@SpringBootTest
@SpringBatchTest
class LibraryMigrationJobTest {
    private static final int TOTAL_AUTHORS = 5;
    private static final int TOTAL_GENRES = 4;
    private static final int TOTAL_BOOKS = 3;
    private static final String EXPECTED_AUTHOR = "Афанасий Афанасьевич Фет";
    private static final String EXPECTED_GENRE = "поэзия";
    private static final String BOOK_TITLE = "Стихотворения";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoGenreRepository mongoGenreRepository;

    @Autowired
    private MongoAuthorRepository mongoAuthorRepository;

    @Autowired
    private MongoBookRepository mongoBookRepository;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(LIBRARY_MIGRATION_JOB_NAME);

        JobParameters parameters = new JobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        assertEquals(TOTAL_AUTHORS, mongoAuthorRepository.findAll().size());
        assertEquals(TOTAL_GENRES, mongoGenreRepository.findAll().size());
        assertEquals(TOTAL_BOOKS, mongoBookRepository.findAll().size());

        MongoBook actualMongoBook = mongoBookRepository.findByTitle(BOOK_TITLE).get(0);
        assertEquals(EXPECTED_AUTHOR, actualMongoBook.getAuthors().get(0).getName());
        assertEquals(EXPECTED_GENRE, actualMongoBook.getGenres().get(0).getName());
    }
}