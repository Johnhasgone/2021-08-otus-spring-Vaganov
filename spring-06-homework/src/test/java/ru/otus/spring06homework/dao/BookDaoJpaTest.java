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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами должно ")
@DataJpaTest
@Import(BookDaoJpa.class)
@ActiveProfiles("test")
class BookDaoJpaTest {
    private static final String FIRST_BOOK_TITLE = "";
    private static final String SECOND_BOOK_TITLE = "";
    private static final String THIRD_BOOK_TITLE = "Стихотворения";
    private static final String FIRST_AUTHOR_NAME = "Афанасий Афанасьевич Фет";
    private static final String THIRD_AUTHOR_NAME = "Стихотворения";
    private static final String SECOND_GENRE_NAME = "поэзия";
    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final Long SECOND_AUTHOR_ID = 2L;
    private static final Long THIRD_AUTHOR_ID = 3L;

    @Autowired
    private BookDaoJpa bookDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(
                null,
                "Стихотворения",
                List.of(new Author(null, FIRST_AUTHOR_NAME)),
                List.of(new Genre(null, SECOND_GENRE_NAME))
        );
        bookDaoJpa.save(expectedBook);
        Book actualBook = em.find(Book.class, THIRD_AUTHOR_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        String expectedName = "Lyrics";
        Book updatableBook = em.find(Book.class, FIRST_AUTHOR_ID);
        bookDaoJpa.update(
                new Book(
                        FIRST_AUTHOR_ID,
                        expectedName,
                        updatableBook.getAuthors(),
                        updatableBook.getGenres()
                )
        );
        em.detach(updatableBook);
        Book actualBook = em.find(Book.class, FIRST_AUTHOR_ID);
        assertThat(actualBook.getTitle()).isEqualTo(expectedName);
    }

    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetExpectedBookById() {
        Book expectedBook = new Book(
                1L,
                "Стихотворения",
                List.of(new Author(1L, "Афанасий Афанасьевич Фет")),
                List.of(new Genre(2L, "поэзия"))

        );
        Book actualBook = bookDaoJpa.findById(1L).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
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