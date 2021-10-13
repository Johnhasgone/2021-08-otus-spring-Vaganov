package ru.otus.spring05homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring05homework.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с авторами книг должно ")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author("Николай Васильевич Гоголь");
        Long id = authorDaoJdbc.insert(expectedAuthor);
        Author actualAuthor = authorDaoJdbc.getById(id);
        assertThat(actualAuthor.getName()).isEqualTo(expectedAuthor.getName());
    }

    @DisplayName("обновлять автора в БД")
    @Test
    void shouldUpdateAuthor() {
        String expectedName = "Осип Мандельштам";
        Author updatableAuthor = authorDaoJdbc.getAll().get(0);
        authorDaoJdbc.update(new Author(updatableAuthor.getId(), expectedName));
        Author actualAuthor = authorDaoJdbc.getById(updatableAuthor.getId());
        assertThat(actualAuthor.getName()).isEqualTo(expectedName);
    }

    @DisplayName("получать автора по ID")
    @Test
    void shouldGetExpectedAuthorById() {
        Author expectedAuthor = new Author(1L, "Афанасий Афанасьевич Фет");
        Author actualAuthor = authorDaoJdbc.getById(1L);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("получать автора по имени")
    @Test
    void shouldGetAuthorByName() {
        Author expectedAuthor = new Author(1L, "Афанасий Афанасьевич Фет");
        Author actualAuthor = authorDaoJdbc.getByName(expectedAuthor.getName());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("получать всех авторов из БД")
    @Test
    void shouldGetAllAuthors() {
        List<Author> expectedAuthors = List.of(new Author(1L, "Афанасий Афанасьевич Фет"), new Author(2L, "Сергей Михалков"));
        List<Author> actualAuthors = authorDaoJdbc.getAll();
        assertThat(actualAuthors)
                .containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @DisplayName("удалять автора по ID")
    @Test
    void shouldDeleteAuthorById() {
        authorDaoJdbc.deleteById(2L);
        assertThat(authorDaoJdbc.getById(2L)).isNull();
    }
}