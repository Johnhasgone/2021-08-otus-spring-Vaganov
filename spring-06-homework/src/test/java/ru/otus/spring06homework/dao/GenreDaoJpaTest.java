package ru.otus.spring06homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring06homework.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с жанрами книг должно ")
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {
    private static final Long FIRST_GENRE_ID = 1L;
    private static final Long SECOND_GENRE_ID = 2L;
    private static final Long THIRD_GENRE_ID = 3L;
    private static final Long FORTH_GENRE_ID = 4L;
    private static final String CREATED_GENRE_NAME = "пьеса";
    private static final String FIRST_GENRE_NAME = "проза";
    private static final String EXPECTED_GENRE_NAME = "роман";

    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(null,CREATED_GENRE_NAME);
        genreDaoJpa.save(expectedGenre);
        Genre actualGenre = em.find(Genre.class, expectedGenre.getId());
        assertThat(actualGenre.getId()).isNotNull().isGreaterThan(0);
        assertThat(actualGenre.getName()).isEqualTo(expectedGenre.getName());
    }

    @DisplayName("обновлять жанр в БД")
    @Test
    void shouldUpdateGenre() {
        Genre updatingGenre = genreDaoJpa.findAll().get(0);
        em.detach(updatingGenre);
        genreDaoJpa.updateNameById(updatingGenre.getId(), EXPECTED_GENRE_NAME);
        Genre actualGenre = em.find(Genre.class, updatingGenre.getId());
        assertThat(actualGenre.getName()).isEqualTo(EXPECTED_GENRE_NAME);
    }

    @DisplayName("получать жанр по ID")
    @Test
    void shouldGetExpectedGenreById() {
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        Optional<Genre> actualGenre = genreDaoJpa.findById(FIRST_GENRE_ID);
        assertThat(actualGenre).isPresent().get().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("получать жанр по имени")
    @Test
    void shouldGetGenreByName() {
        List<Genre> expectedGenres = List.of(em.find(Genre.class, FIRST_GENRE_ID));
        List<Genre> actualGenres = genreDaoJpa.findByName(FIRST_GENRE_NAME);
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @DisplayName("получать все жанры из БД")
    @Test
    void shouldGetAllGenres() {
        List<Genre> expectedGenres = List.of(
                em.find(Genre.class, FIRST_GENRE_ID),
                em.find(Genre.class, SECOND_GENRE_ID),
                em.find(Genre.class, THIRD_GENRE_ID),
                em.find(Genre.class, FORTH_GENRE_ID)
        );
        List<Genre> actualGenres = genreDaoJpa.findAll();
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @DisplayName("удалять жанр по ID")
    @Test
    void shouldDeleteGenreById() {
        Genre deletingGenre = em.find(Genre.class, FORTH_GENRE_ID);
        assertThat(deletingGenre).isNotNull();
        genreDaoJpa.deleteById(FORTH_GENRE_ID);
        em.detach(deletingGenre);
        Genre deletedGenre = em.find(Genre.class, FORTH_GENRE_ID);
        assertThat(deletedGenre).isNull();
    }
}