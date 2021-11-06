package ru.otus.spring08homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring08homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с жанрами книг должно ")
@DataJpaTest
class GenreRepositoryTest {
    private static final Long GENRE_ID = 1L;
    private static final String GENRE_NAME = "проза";
    private static final String EXPECTED_GENRE_NAME = "роман";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получать жанр по имени")
    @Test
    void shouldGetGenreByName() {
        List<Genre> expectedGenres = List.of(em.find(Genre.class, GENRE_ID));
        List<Genre> actualGenres = genreRepository.findByName(GENRE_NAME);
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }
}