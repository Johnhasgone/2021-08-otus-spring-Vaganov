package ru.otus.spring05homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring05homework.domain.Author;
import ru.otus.spring05homework.domain.Book;
import ru.otus.spring05homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами должно ")
@JdbcTest
@Import(BookDaoJpa.class)
@ActiveProfiles("test")
class BookDaoJpaTest {

    @Autowired
    private BookDaoJpa bookDaoJpa;



    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(
                3L,
                "Стихотворения",
                new Genre(2L, "поэзия"),
                new Author(1L, "Афанасий Афанасьевич Фет")
        );
        Long id = bookDaoJpa.insert(expectedBook);
        Book actualBook = bookDaoJpa.getById(id);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        String expectedName = "Lyrics";
        Book updatableBook = bookDaoJpa.getAll().get(0);
        bookDaoJpa.update(
                new Book(
                        updatableBook.getId(),
                        expectedName,
                        updatableBook.getGenre(),
                        updatableBook.getAuthor()
                )
        );
        Book actualBook = bookDaoJpa.getById(updatableBook.getId());
        assertThat(actualBook.getTitle()).isEqualTo(expectedName);
    }

    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetExpectedBookById() {
        Book expectedBook = new Book(
                1L,
                "Стихотворения",
                new Genre(2L, "поэзия"),
                new Author(1L, "Афанасий Афанасьевич Фет")
        );
        Book actualBook = bookDaoJpa.getById(1L);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("получать книгу по имени")
    @Test
    void shouldGetBookByName() {
        Book expectedBook = new Book(
                1L,
                "Стихотворения",
                new Genre(2L, "поэзия"),
                new Author(1L, "Афанасий Афанасьевич Фет")
        );
        Book actualBook = bookDaoJpa.getByTitle(expectedBook.getTitle());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("получать всех авторов из БД")
    @Test
    void shouldGetAllBooks() {
        List<Book> expectedBooks = List.of(
                new Book(1L, "Стихотворения", new Genre(2L, "поэзия"), new Author(1L, "Афанасий Афанасьевич Фет")),
                new Book(2L, "Сборник рассказов", new Genre(1L, "проза"), new Author(2L, "Сергей Михалков"))
        );
        List<Book> actualBooks = bookDaoJpa.getAll();
        assertThat(actualBooks)
                .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DisplayName("удалять книгу по ID")
    @Test
    void shouldDeleteBookById() {
        bookDaoJpa.deleteById(2L);
        assertThat(bookDaoJpa.getById(2L)).isNull();
    }
}