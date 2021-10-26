package ru.otus.spring06homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring06homework.domain.Author;
import ru.otus.spring06homework.domain.Book;
import ru.otus.spring06homework.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами должно ")
@DataJpaTest
@Import(BookDaoJpa.class)
@ActiveProfiles("test")
class BookDaoJpaTest {
    private static final String CREATING_BOOK_TITLE = "Размышления";
    private static final String CREATING_AUTHOR_NAME = "Марк Аврелий";
    private static final String CREATING_GENRE_NAME_1 = "Философия";
    private static final String CREATING_GENRE_NAME_2 = "Автобиография";

    private static final Long FIRST_BOOK_ID = 1L;
    private static final Long SECOND_BOOK_ID = 2L;
    private static final Long THIRD_BOOK_ID = 3L;




    @Autowired
    private BookDaoJpa bookDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(
                null,
                CREATING_BOOK_TITLE,
                List.of(new Author(null, CREATING_AUTHOR_NAME)),
                List.of(new Genre(null, CREATING_GENRE_NAME_1), new Genre(null, CREATING_GENRE_NAME_2))
        );
        bookDaoJpa.save(expectedBook);
        Book actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(actualBook).isNotNull()
                .matches(s -> s.getTitle().equals(CREATING_BOOK_TITLE))
                .matches(s -> s.getAuthors().get(0).getName().equals(CREATING_AUTHOR_NAME))
                .matches(s -> s.getGenres().size() == 2)
        ;
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        String expectedName = "Lyrics";
        Book updatableBook = em.find(Book.class, THIRD_BOOK_ID);
        bookDaoJpa.updateNameById(THIRD_BOOK_ID, expectedName);
        em.detach(updatableBook);
        Book actualBook = em.find(Book.class, THIRD_BOOK_ID);
        assertThat(actualBook.getTitle()).isEqualTo(expectedName);
    }

    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetExpectedBookById() {
        Book expectedBook = em.find(Book.class, THIRD_BOOK_ID);
        Optional<Book> actualBook = bookDaoJpa.findById(THIRD_BOOK_ID);
        assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("получать книгу по имени")
    @Test
    void shouldGetBookByName() {
        List<Book> expectedBooks = List.of(em.find(Book.class, THIRD_BOOK_ID));
        List<Book> actualBooks = bookDaoJpa.findByTitle(expectedBooks.get(0).getTitle());
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DisplayName("получать все книги из БД")
    @Test
    void shouldGetAllBooks() {
        List<Book> expectedBooks = List.of(
                em.find(Book.class, FIRST_BOOK_ID),
                em.find(Book.class, SECOND_BOOK_ID),
                em.find(Book.class, THIRD_BOOK_ID)
        );
        List<Book> actualBooks = bookDaoJpa.findAll();
        assertThat(actualBooks).isNotNull()
                .allMatch(book -> book.getTitle() != null)
                .allMatch(book -> book.getAuthors()!= null && !book.getAuthors().isEmpty())
                .allMatch(book -> book.getGenres() != null && !book.getGenres().isEmpty())
                .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DisplayName("удалять книгу по ID")
    @Test
    void shouldDeleteBookById() {
        Book deletingBook = em.find(Book.class, SECOND_BOOK_ID);
        bookDaoJpa.deleteById(SECOND_BOOK_ID);
        em.detach(deletingBook);
        Book deletedBook = em.find(Book.class, SECOND_BOOK_ID);
        assertThat(deletedBook).isNull();
    }
}