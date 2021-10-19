package ru.otus.spring05homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring05homework.domain.Author;
import ru.otus.spring05homework.domain.Book;
import ru.otus.spring05homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами должно ")
@DataJpaTest
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
                List.of(new Author(1L, "Афанасий Афанасьевич Фет")),
                List.of(new Genre(2L, "поэзия"))
        );
        Book actualBook = bookDaoJpa.save(expectedBook);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        String expectedName = "Lyrics";
        Book updatableBook = bookDaoJpa.findAll().get(0);
        bookDaoJpa.update(
                new Book(
                        updatableBook.getId(),
                        expectedName,
                        updatableBook.getAuthors(),
                        updatableBook.getGenres()
                )
        );
        Book actualBook = bookDaoJpa.findById(updatableBook.getId()).get();
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