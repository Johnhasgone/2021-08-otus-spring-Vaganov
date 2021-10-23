package ru.otus.spring06homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring06homework.dao.BookDao;
import ru.otus.spring06homework.domain.Author;
import ru.otus.spring06homework.domain.Book;
import ru.otus.spring06homework.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с книгами должен ")
@SpringBootTest
class BookServiceImplTest {

    private static final Long FIRST_BOOK_ID = 1L;
    private static final Long SECOND_BOOK_ID = 2L;
    private static final String FIRST_BOOK_TITLE = "Стихотворения";
    private static final String SECOND_BOOK_TITLE = "Рассказы";
    private static final String EXPECTED_TITLE = "LYRICS";

    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final Long SECOND_AUTHOR_ID = 2L;
    private static final String FIRST_AUTHOR_NAME = "Афанасий Афанасьевич Фет";
    private static final String SECOND_AUTHOR_NAME = "Сергей Михалков";

    private static final Long FIRST_GENRE_ID = 1L;
    private static final Long SECOND_GENRE_ID = 2L;
    private static final String FIRST_GENRE_NAME = "поэзия";
    private static final String SECOND_GENRE_NAME = "проза";


    @Autowired
    BookService bookService;
    @MockBean
    BookDao bookDao;

    @DisplayName("возвращать ожидаемую книгу по ID")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME)));
        when(bookDao.findById(any())).thenReturn(Optional.of(expectedBook));
        Optional<Book> actualBook = bookService.findById(FIRST_BOOK_ID);
        assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по названию")
    @Test
    void shouldReturnExpectedBookByName() {
        List<Book> expectedBooks = List.of(
                new Book(
                        FIRST_BOOK_ID,
                        FIRST_BOOK_TITLE,
                        List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                        List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
                )
        );
        when(bookDao.findByTitle(any())).thenReturn(expectedBooks);
        List<Book> actualBooks = bookService.findByTitle(FIRST_BOOK_TITLE);
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DisplayName("возвращать все книги")
    @Test
    void shouldReturnAllBooks() {
        List<Book> expected = List.of(
                new Book(
                        FIRST_BOOK_ID,
                        FIRST_BOOK_TITLE,
                        List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                        List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
                ),
                new Book(
                        SECOND_BOOK_ID,
                        SECOND_BOOK_TITLE,
                        List.of(new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME)),
                        List.of(new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME))
                )
        );
        when(bookDao.findAll()).thenReturn(expected);
        List<Book> actual = bookService.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("возвращать ID созданной книги")
    @Test
    void shouldCreateBook() {
        Book creatingBook = new Book(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
        );
        when(bookDao.save(any())).thenReturn(creatingBook);
        Book actualBook = bookService.save(creatingBook);
        assertThat(actualBook).isEqualTo(creatingBook);
    }

    @DisplayName("возвращать true при обновлении названия книги")
    @Test
    void shouldReturnTrueForSuccessfulBookUpdate() {
        Book updatingBook = new Book(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
        );
        when(bookDao.updateNameById(FIRST_BOOK_ID, EXPECTED_TITLE)).thenReturn(1);
        assertThat(bookService.updateNameById(FIRST_BOOK_ID, EXPECTED_TITLE)).isTrue();
        verify(bookDao).updateNameById(FIRST_BOOK_ID, EXPECTED_TITLE);

    }

    @DisplayName("вызывать bookDao.delete()")
    @Test
    void shouldInvokeDeleteInDao() {
        when(bookDao.deleteById(FIRST_BOOK_ID)).thenReturn(1);
        assertThat(bookService.deleteById(FIRST_BOOK_ID)).isTrue();
    }
}