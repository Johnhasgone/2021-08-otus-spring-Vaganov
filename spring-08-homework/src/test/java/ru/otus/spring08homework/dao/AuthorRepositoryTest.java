package ru.otus.spring08homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring08homework.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с авторами книг должно ")
@DataJpaTest
class AuthorRepositoryTest {
    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Афанасий Афанасьевич Фет";
    private static final String EXPECTED_AUTHOR_NAME = "Осип Мандельштам";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("обновлять автора в БД")
    @Test
    void shouldUpdateAuthor() {
        Author updatableAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        authorRepository.updateNameById(FIRST_AUTHOR_ID, EXPECTED_AUTHOR_NAME);
        em.detach(updatableAuthor);
        Author actualAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(actualAuthor.getName()).isEqualTo(EXPECTED_AUTHOR_NAME);
    }

    @DisplayName("получать автора по имени")
    @Test
    void shouldGetAuthorByName() {
        List<Author> expectedAuthor = List.of(em.find(Author.class, FIRST_AUTHOR_ID));
        List<Author> actualAuthor = authorRepository.findByName(FIRST_AUTHOR_NAME);
        assertThat(actualAuthor).containsExactlyInAnyOrderElementsOf(expectedAuthor);
    }
}