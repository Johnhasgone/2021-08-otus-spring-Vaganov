package ru.otus.spring05homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.spring05homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DAO для работы с жанрами книг должно ")
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre("пьеса");
        Genre actualGenre = genreDaoJpa.save(expectedGenre);
        assertThat(actualGenre.getName()).isEqualTo(expectedGenre.getName());
    }

    @DisplayName("обновлять жанр в БД")
    @Test
    void shouldUpdateGenre() {
        String expectedName = "роман";
        Genre updatableGenre = genreDaoJpa.findAll().get(0);
        genreDaoJpa.updateNameById(updatableGenre.getId(), expectedName);
        Genre actualGenre = genreDaoJpa.findById(updatableGenre.getId()).get();
        assertThat(actualGenre.getName()).isEqualTo(expectedName);
    }

    @DisplayName("получать жанр по ID")
    @Test
    void shouldGetExpectedGenreById() {
        Genre expectedGenre = new Genre(1L, "проза");
        Genre actualGenre = genreDaoJpa.findById(1L).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("получать жанр по имени")
    @Test
    void shouldGetGenreByName() {
        List<Genre> expectedGenres = List.of(new Genre(1L, "проза"));
        List<Genre> actualGenres = genreDaoJpa.findByName(expectedGenres.get(0).getName());
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @DisplayName("получать все жанры из БД")
    @Test
    void shouldGetAllGenres() {
        List<Genre> expectedGenres = List.of(
                new Genre(1L, "проза"),
                new Genre(2L, "поэзия"),
                new Genre(3L, "комедия")
        );
        List<Genre> actualGenres = genreDaoJpa.findAll();
        System.out.println(actualGenres.get(0).getId());
        System.out.println(expectedGenres.get(0).getId());
        assertThat(actualGenres)
                .containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @DisplayName("удалять жанр по ID")
    @Test
    void shouldDeleteGenreById() {
        genreDaoJpa.deleteById(3L);
        assertThat(genreDaoJpa.findById(3L)).isNull();
    }

    @DisplayName("выбрасывать исключение при удалении жанра на который имеются ссылки из других таблиц")
    @Test
    void  shouldThrowExceptionOnDeletingLinkedGenre() {
        assertThrows(DataIntegrityViolationException.class, () -> genreDaoJpa.deleteById(2L));
    }
}