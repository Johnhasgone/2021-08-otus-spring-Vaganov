package ru.otus.spring16homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.spring16homework.dao.BookRepository;
import ru.otus.spring16homework.domain.Author;
import ru.otus.spring16homework.domain.Book;
import ru.otus.spring16homework.domain.Genre;
import ru.otus.spring16homework.dto.BookDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами должен ")
@SpringBootTest
class BookServiceImplTest {

    private static final Long FIRST_BOOK_ID = 1L;
    private static final Long SECOND_BOOK_ID = 2L;
    private static final String FIRST_BOOK_TITLE = "Стихотворения";
    private static final String SECOND_BOOK_TITLE = "Рассказы";

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
    BookRepository bookRepository;

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
                FIRST_AUTHOR_NAME,
                FIRST_GENRE_NAME
        );

        when(bookRepository.findBookById(any())).thenReturn(book);
        BookDto actualBook = bookService.findById(FIRST_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBookDto);
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
                        FIRST_AUTHOR_NAME,
                        FIRST_GENRE_NAME
                )
        );
        when(bookRepository.findByTitle(any())).thenReturn(expectedBooks);
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
                        FIRST_AUTHOR_NAME,
                        FIRST_GENRE_NAME
                ),
                new BookDto(
                        SECOND_BOOK_ID,
                        SECOND_BOOK_TITLE,
                        SECOND_AUTHOR_NAME,
                        SECOND_GENRE_NAME
                )
        );
        when(bookRepository.findAll()).thenReturn(expectedBooks);
        List<BookDto> actual = bookService.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expectedBookDtos);
    }

    @DisplayName("возвращать ДТО созданной книги")
    @Test
    @WithMockUser(username = "user")
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
                FIRST_AUTHOR_NAME,
                FIRST_GENRE_NAME
        );
        when(bookRepository.save(any())).thenReturn(creatingBook);
        BookDto actualBook = bookService.save(new BookDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE, FIRST_AUTHOR_NAME, FIRST_GENRE_NAME));
        assertThat(actualBook).isEqualTo(createdBookDto);
    }
}