package ru.otus.spring06homework.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring06homework.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с авторами книг должно ")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {
    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final Long SECOND_AUTHOR_ID = 2L;
    private static final Long THIRD_AUTHOR_ID = 3L;
    private static final Long FORTH_AUTHOR_ID = 4L;
    private static final Long FIFTH_AUTHOR_ID = 5L;
    private static final String CREATED_AUTHOR_NAME = "Николай Васильевич Гоголь";
    private static final String FIRST_AUTHOR_NAME = "Афанасий Афанасьевич Фет";
    private static final String EXPECTED_AUTHOR_NAME = "Осип Мандельштам";

    @Autowired
    private AuthorDaoJpa authorDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(null, CREATED_AUTHOR_NAME);
        authorDaoJpa.save(expectedAuthor);
        Author actualAuthor = em.find(Author.class, expectedAuthor.getId());
        assertThat(actualAuthor.getId()).isNotNull().isGreaterThan(0L);
        assertThat(actualAuthor.getName()).isEqualTo(expectedAuthor.getName());
    }

    @DisplayName("обновлять автора в БД")
    @Test
    void shouldUpdateAuthor() {
        Author updatableAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        authorDaoJpa.updateNameById(FIRST_AUTHOR_ID, EXPECTED_AUTHOR_NAME);
        em.detach(updatableAuthor);
        Author actualAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(actualAuthor.getName()).isEqualTo(EXPECTED_AUTHOR_NAME);
    }

    @DisplayName("получать автора по ID")
    @Test
    void shouldGetExpectedAuthorById() {
        Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        Optional<Author> actualAuthor = authorDaoJpa.findById(FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isPresent().get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("получать автора по имени")
    @Test
    void shouldGetAuthorByName() {
        List<Author> expectedAuthor = List.of(em.find(Author.class, FIRST_AUTHOR_ID));
        List<Author> actualAuthor = authorDaoJpa.findByName(FIRST_AUTHOR_NAME);
        assertThat(actualAuthor).containsExactlyInAnyOrderElementsOf(expectedAuthor);
    }

    @DisplayName("получать всех авторов из БД")
    @Test
    void shouldGetAllAuthors() {
        List<Author> expectedAuthors = List.of(
                em.find(Author.class, FIRST_AUTHOR_ID),
                em.find(Author.class, SECOND_AUTHOR_ID),
                em.find(Author.class, THIRD_AUTHOR_ID),
                em.find(Author.class, FORTH_AUTHOR_ID),
                em.find(Author.class, FIFTH_AUTHOR_ID)
        );
        List<Author> actualAuthors = authorDaoJpa.findAll();
        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @DisplayName("удалять автора по ID")
    @Test
    void shouldDeleteAuthorById() {
        Author deletingAuthor = em.find(Author.class, THIRD_AUTHOR_ID);
        assertThat(deletingAuthor).isNotNull();
        em.detach(deletingAuthor);
        authorDaoJpa.deleteById(THIRD_AUTHOR_ID);
        Author deletedAuthor = em.find(Author.class, THIRD_AUTHOR_ID);
        Assertions.assertThat(deletedAuthor).isNull();
    }
}