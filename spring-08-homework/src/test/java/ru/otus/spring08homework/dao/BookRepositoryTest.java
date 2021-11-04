package ru.otus.spring08homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring08homework.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами должно ")
@DataJpaTest
class BookRepositoryTest {
    private static final String EXPECTED_BOOK_TITLE = "Lyrics";
    private static final Long BOOK_ID = 3L;


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        Book updatableBook = em.find(Book.class, BOOK_ID);
        bookRepository.updateNameById(BOOK_ID, EXPECTED_BOOK_TITLE);
        em.detach(updatableBook);
        Book actualBook = em.find(Book.class, BOOK_ID);
        assertThat(actualBook.getTitle()).isEqualTo(EXPECTED_BOOK_TITLE);
    }

    @DisplayName("получать книгу по имени")
    @Test
    void shouldGetBookByName() {
        List<Book> expectedBooks = List.of(em.find(Book.class, BOOK_ID));
        List<Book> actualBooks = bookRepository.findByTitle(expectedBooks.get(0).getTitle());
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }
}