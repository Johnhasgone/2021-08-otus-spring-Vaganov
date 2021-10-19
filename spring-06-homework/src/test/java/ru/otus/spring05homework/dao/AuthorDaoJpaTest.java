package ru.otus.spring05homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.spring05homework.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DAO для работы с авторами книг должно ")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

    @Autowired
    private AuthorDaoJpa authorDaoJpa;

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author("Николай Васильевич Гоголь");
        Author actualAuthor = authorDaoJpa.save(expectedAuthor);
        assertThat(actualAuthor.getName()).isEqualTo(expectedAuthor.getName());
    }

    @DisplayName("обновлять автора в БД")
    @Test
    void shouldUpdateAuthor() {
        String expectedName = "Осип Мандельштам";
        Author updatableAuthor = authorDaoJpa.findAll().get(0);
        authorDaoJpa.updateNameById(updatableAuthor.getId(), expectedName);
        Author actualAuthor = authorDaoJpa.findById(updatableAuthor.getId()).get();
        assertThat(actualAuthor.getName()).isEqualTo(expectedName);
    }

    @DisplayName("получать автора по ID")
    @Test
    void shouldGetExpectedAuthorById() {
        Author expectedAuthor = new Author(1L, "Афанасий Афанасьевич Фет");
        Author actualAuthor = authorDaoJpa.findById(1L).get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("получать автора по имени")
    @Test
    void shouldGetAuthorByName() {
        List<Author> expectedAuthor = List.of(new Author(1L, "Афанасий Афанасьевич Фет"));
        List<Author> actualAuthor = authorDaoJpa.findByName(expectedAuthor.get(0).getName());
        assertThat(actualAuthor).containsExactlyInAnyOrderElementsOf(expectedAuthor);
    }

    @DisplayName("получать всех авторов из БД")
    @Test
    void shouldGetAllAuthors() {
        List<Author> expectedAuthors = List.of(
                new Author(1L, "Афанасий Афанасьевич Фет"),
                new Author(2L, "Сергей Михалков"),
                new Author(3L, "Алексей Толстой")
        );
        List<Author> actualAuthors = authorDaoJpa.findAll();
        assertThat(actualAuthors)
                .containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @DisplayName("удалять автора по ID")
    @Test
    void shouldDeleteAuthorById() {
        authorDaoJpa.deleteById(3L);
        assertThat(authorDaoJpa.findById(3L)).isNull();
    }

    @DisplayName("выбросить исключение при удалении автора, на которого имеются ссылки из других таблиц")
    @Test
    void shouldThrowExceptionOnDeletingLinkedAuthor() {
        assertThrows(DataIntegrityViolationException.class, () -> authorDaoJpa.deleteById(2L));
    }
}