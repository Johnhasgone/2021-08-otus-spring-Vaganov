package ru.otus.spring05homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.spring05homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DAO для работы с жанрами книг должно ")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre("пьеса");
        Long id = genreDaoJdbc.insert(expectedGenre);
        Genre actualGenre = genreDaoJdbc.getById(id);
        assertThat(actualGenre.getName()).isEqualTo(expectedGenre.getName());
    }

    @DisplayName("обновлять жанр в БД")
    @Test
    void shouldUpdateGenre() {
        String expectedName = "роман";
        Genre updatableGenre = genreDaoJdbc.getAll().get(0);
        genreDaoJdbc.update(new Genre(updatableGenre.getId(), expectedName));
        Genre actualGenre = genreDaoJdbc.getById(updatableGenre.getId());
        assertThat(actualGenre.getName()).isEqualTo(expectedName);
    }

    @DisplayName("получать жанр по ID")
    @Test
    void shouldGetExpectedGenreById() {
        Genre expectedGenre = new Genre(1L, "проза");
        Genre actualGenre = genreDaoJdbc.getById(1L);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("получать жанр по имени")
    @Test
    void shouldGetGenreByName() {
        Genre expectedGenre = new Genre(1L, "проза");
        Genre actualGenre = genreDaoJdbc.getByName(expectedGenre.getName());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("получать все жанры из БД")
    @Test
    void shouldGetAllGenres() {
        List<Genre> expectedGenres = List.of(
                new Genre(1L, "проза"),
                new Genre(2L, "поэзия"),
                new Genre(3L, "комедия")
        );
        List<Genre> actualGenres = genreDaoJdbc.getAll();
        System.out.println(actualGenres.get(0).getId());
        System.out.println(expectedGenres.get(0).getId());
        assertThat(actualGenres)
                .containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @DisplayName("удалять жанр по ID")
    @Test
    void shouldDeleteGenreById() {
        genreDaoJdbc.deleteById(3L);
        assertThat(genreDaoJdbc.getById(3L)).isNull();
    }

    @DisplayName("выбрасывать исключение при удалении жанра на который имеются ссылки из других таблиц")
    @Test
    void  shouldThrowExceptionOnDeletingLinkedGenre() {
        assertThrows(DataIntegrityViolationException.class, () -> genreDaoJdbc.deleteById(2L));
    }
}