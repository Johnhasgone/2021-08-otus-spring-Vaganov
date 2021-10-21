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
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        String expectedName = "Lyrics";
        Book updatableBook = em.find(Book.class, FIRST_BOOK_ID);
        bookDaoJpa.updateNameById(FIRST_BOOK_ID, expectedName);
        em.detach(updatableBook);
        Book actualBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook.getTitle()).isEqualTo(expectedName);
    }

    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetExpectedBookById() {
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        Optional<Book> actualBook = bookDaoJpa.findById(1L);
        assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("получать книгу по имени")
    @Test
    void shouldGetBookByName() {
        List<Book> expectedBooks = List.of(new Book(
                1L,
                "Стихотворения",
                List.of(new Author(1L, "Афанасий Афанасьевич Фет")),
                List.of(new Genre(2L, "поэзия"))
        ));
        List<Book> actualBooks = bookDaoJpa.findByTitle(expectedBooks.get(0).getTitle());
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DisplayName("получать всех авторов из БД")
    @Test
    void shouldGetAllBooks() {
        List<Book> expectedBooks = List.of(
                new Book(1L, "Стихотворения", List.of(new Author(1L, "Афанасий Афанасьевич Фет")), List.of(new Genre(2L, "поэзия"))),
                new Book(2L, "Сборник рассказов", List.of(new Author(2L, "Сергей Михалков")), List.of(new Genre(1L, "проза")))
        );
        List<Book> actualBooks = bookDaoJpa.findAll();
        assertThat(actualBooks)
                .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DisplayName("удалять книгу по ID")
    @Test
    void shouldDeleteBookById() {
        bookDaoJpa.deleteById(2L);
        assertThat(bookDaoJpa.findById(2L)).isNull();
    }
}