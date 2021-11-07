package ru.otus.spring08homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring08homework.domain.Genre;

import java.util.List;

@DisplayName("DAO для работы с жанрами книг должно ")
@DataMongoTest
@EnableConfigurationProperties
class GenreRepositoryTest {
    private static final Long GENRE_ID = 1L;
    private static final String GENRE_NAME = "проза";
    private static final String EXPECTED_GENRE_NAME = "роман";

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("получать жанр по имени")
    @Test
    void shouldGetGenreByName() {
        List<Genre> actualGenres = genreRepository.findByName(GENRE_NAME);
    }
}