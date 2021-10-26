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
import ru.otus.spring06homework.dto.AuthorDto;
import ru.otus.spring06homework.dto.BookDto;
import ru.otus.spring06homework.dto.GenreDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Book book = new Book(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
        );
        BookDto expectedBookDto = new BookDto(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
        );

        when(bookDao.findById(any())).thenReturn(Optional.of(book));
        Optional<BookDto> actualBook = bookService.findById(FIRST_BOOK_ID);
        assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBookDto);
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

        List<BookDto> expectedBookDtos = List.of(
                new BookDto(
                        FIRST_BOOK_ID,
                        FIRST_BOOK_TITLE,
                        List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                        List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
                )
        );
        when(bookDao.findByTitle(any())).thenReturn(expectedBooks);
        List<BookDto> actualBooks = bookService.findByTitle(FIRST_BOOK_TITLE);
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBookDtos);
    }

    @DisplayName("возвращать все книги")
    @Test
    void shouldReturnAllBooks() {
        List<Book> expectedBooks = List.of(
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

        List<BookDto> expectedBookDtos = List.of(
                new BookDto(
                        FIRST_BOOK_ID,
                        FIRST_BOOK_TITLE,
                        List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                        List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
                ),
                new BookDto(
                        SECOND_BOOK_ID,
                        SECOND_BOOK_TITLE,
                        List.of(new AuthorDto(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME)),
                        List.of(new GenreDto(SECOND_GENRE_ID, SECOND_GENRE_NAME))
                )
        );
        when(bookDao.findAll()).thenReturn(expectedBooks);
        List<BookDto> actual = bookService.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expectedBookDtos);
    }

    @DisplayName("возвращать ДТО созданной книги")
    @Test
    void shouldCreateBook() {
        Book creatingBook = new Book(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
        );

        BookDto createdBookDto = new BookDto(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
                List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
        );
        when(bookDao.save(any())).thenReturn(creatingBook);
        BookDto actualBook = bookService.save(FIRST_BOOK_TITLE, List.of(FIRST_AUTHOR_NAME), List.of(FIRST_GENRE_NAME));
        assertThat(actualBook).isEqualTo(createdBookDto);
    }

    @DisplayName("возвращать true при обновлении названия книги")
    @Test
    void shouldReturnTrueForSuccessfulBookUpdate() {
        when(bookDao.updateNameById(FIRST_BOOK_ID, EXPECTED_TITLE)).thenReturn(1);
        assertThat(bookService.updateNameById(FIRST_BOOK_ID, EXPECTED_TITLE)).isTrue();
        verify(bookDao).updateNameById(FIRST_BOOK_ID, EXPECTED_TITLE);

    }

    @DisplayName("возвращать true при успешном удалении книги")
    @Test
    void shouldInvokeDeleteInDao() {
        when(bookDao.deleteById(FIRST_BOOK_ID)).thenReturn(1);
        assertThat(bookService.deleteById(FIRST_BOOK_ID)).isTrue();
    }
}