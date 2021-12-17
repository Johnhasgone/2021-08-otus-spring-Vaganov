//package ru.otus.spring11homework.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import ru.otus.spring11homework.dao.AuthorRepository;
//import ru.otus.spring11homework.dao.BookRepository;
//import ru.otus.spring11homework.dao.CommentRepository;
//import ru.otus.spring11homework.dao.GenreRepository;
//import ru.otus.spring11homework.domain.Author;
//import ru.otus.spring11homework.domain.Book;
//import ru.otus.spring11homework.domain.Comment;
//import ru.otus.spring11homework.domain.Genre;
//import ru.otus.spring11homework.dto.AuthorDto;
//import ru.otus.spring11homework.dto.BookDto;
//import ru.otus.spring11homework.dto.GenreDto;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@DisplayName("Сервис для работы с книгами должен ")
//@SpringBootTest
//class BookServiceImplTest {
//
//    private static final String FIRST_BOOK_ID = "1";
//    private static final String SECOND_BOOK_ID = "2";
//    private static final String FIRST_BOOK_TITLE = "Стихотворения";
//    private static final String SECOND_BOOK_TITLE = "Рассказы";
//    private static final String EXPECTED_TITLE = "LYRICS";
//
//    private static final String FIRST_AUTHOR_ID = "1";
//    private static final String SECOND_AUTHOR_ID = "2";
//    private static final String FIRST_AUTHOR_NAME = "Афанасий Афанасьевич Фет";
//    private static final String SECOND_AUTHOR_NAME = "Сергей Михалков";
//
//    private static final String FIRST_GENRE_ID = "1";
//    private static final String SECOND_GENRE_ID = "2";
//    private static final String FIRST_GENRE_NAME = "поэзия";
//    private static final String SECOND_GENRE_NAME = "проза";
//
//
//    @Autowired
//    private BookService bookService;
//    @MockBean
//    private BookRepository bookRepository;
//    @MockBean
//    private AuthorRepository authorRepository;
//    @MockBean
//    private GenreRepository genreRepository;
//    @MockBean
//    private CommentRepository commentRepository;
//
//
//    @DisplayName("возвращать ожидаемую книгу по ID")
//    @Test
//    void shouldReturnExpectedBookById() {
//        Book book = new Book(
//                FIRST_BOOK_ID,
//                FIRST_BOOK_TITLE,
//                List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//        );
//        BookDto expectedBookDto = new BookDto(
//                FIRST_BOOK_ID,
//                FIRST_BOOK_TITLE,
//                List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//        );
//
//        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
//        Optional<BookDto> actualBook = bookService.findById(FIRST_BOOK_ID);
//        assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBookDto);
//    }
//
//    @DisplayName("возвращать ожидаемую книгу по названию")
//    @Test
//    void shouldReturnExpectedBookByName() {
//        List<Book> expectedBooks = List.of(
//                new Book(
//                        FIRST_BOOK_ID,
//                        FIRST_BOOK_TITLE,
//                        List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                        List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//                )
//        );
//
//        List<BookDto> expectedBookDtos = List.of(
//                new BookDto(
//                        FIRST_BOOK_ID,
//                        FIRST_BOOK_TITLE,
//                        List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                        List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//                )
//        );
//        when(bookRepository.findByTitle(any())).thenReturn(expectedBooks);
//        List<BookDto> actualBooks = bookService.findByTitle(FIRST_BOOK_TITLE);
//        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBookDtos);
//    }
//
//    @DisplayName("возвращать все книги")
//    @Test
//    void shouldReturnAllBooks() {
//        List<Book> expectedBooks = List.of(
//                new Book(
//                        FIRST_BOOK_ID,
//                        FIRST_BOOK_TITLE,
//                        List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                        List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//                ),
//                new Book(
//                        SECOND_BOOK_ID,
//                        SECOND_BOOK_TITLE,
//                        List.of(new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME)),
//                        List.of(new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME))
//                )
//        );
//
//        List<BookDto> expectedBookDtos = List.of(
//                new BookDto(
//                        FIRST_BOOK_ID,
//                        FIRST_BOOK_TITLE,
//                        List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                        List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//                ),
//                new BookDto(
//                        SECOND_BOOK_ID,
//                        SECOND_BOOK_TITLE,
//                        List.of(new AuthorDto(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME)),
//                        List.of(new GenreDto(SECOND_GENRE_ID, SECOND_GENRE_NAME))
//                )
//        );
//        when(bookRepository.findAll()).thenReturn(expectedBooks);
//        List<BookDto> actual = bookService.findAll();
//        assertThat(actual).containsExactlyInAnyOrderElementsOf(expectedBookDtos);
//    }
//
//    @DisplayName("возвращать ДТО созданной книги")
//    @Test
//    void shouldCreateBook() {
//        Author author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
//        Genre genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
//        Book creatingBook = new Book(
//                FIRST_BOOK_ID,
//                FIRST_BOOK_TITLE,
//                List.of(author),
//                List.of(genre)
//        );
//
//        BookDto createdBookDto = new BookDto(
//                FIRST_BOOK_ID,
//                FIRST_BOOK_TITLE,
//                List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//        );
//        when(bookRepository.save(any())).thenReturn(creatingBook);
//        when(authorRepository.findByName(FIRST_AUTHOR_NAME)).thenReturn(List.of(author));
//        when(genreRepository.findByName(FIRST_GENRE_NAME)).thenReturn(List.of(genre));
//        BookDto actualBook = bookService.save(FIRST_BOOK_TITLE, List.of(FIRST_AUTHOR_NAME), List.of(FIRST_GENRE_NAME));
//        assertThat(actualBook).isEqualTo(createdBookDto);
//    }
//
//    @DisplayName("при удалении книги удалять и ее комментарии")
//    @Test
//    void shouldDeleteBookWithComments() {
//        List<Comment> comments = List.of(new Comment());
//        when(commentRepository.findByBookId(any())).thenReturn(comments);
//        doNothing().when(commentRepository).deleteAll(any());
//        doNothing().when(bookRepository).deleteById(any());
//
//        bookService.deleteById(FIRST_BOOK_ID);
//
//        verify(commentRepository, times(1)).findByBookId(FIRST_BOOK_ID);
//        verify(commentRepository, times(1)).deleteAll(comments);
//        verify(bookRepository, times(1)).deleteById(FIRST_BOOK_ID);
//    }
//
//    @DisplayName("находить книги по id автора")
//    @Test
//    void shouldFindBooksByAuthor() {
//        Book book = new Book(
//                FIRST_BOOK_ID,
//                FIRST_BOOK_TITLE,
//                List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//        );
//        List<BookDto> expected = List.of(new BookDto(
//                FIRST_BOOK_ID,
//                FIRST_BOOK_TITLE,
//                List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//        ));
//
//        when(bookRepository.findByAuthorsContaining(any())).thenReturn(List.of(book));
//        List<BookDto> actual = bookService.findByAuthorsContaining(FIRST_AUTHOR_ID);
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @DisplayName("находить книги по id жанра")
//    @Test
//    void shouldFindBooksByGenre() {
//        Book book = new Book(
//                FIRST_BOOK_ID,
//                FIRST_BOOK_TITLE,
//                List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//        );
//        List<BookDto> expected = List.of(new BookDto(
//                FIRST_BOOK_ID,
//                FIRST_BOOK_TITLE,
//                List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME)),
//                List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME))
//        ));
//
//        when(bookRepository.findByGenresContaining(any())).thenReturn(List.of(book));
//        List<BookDto> actual = bookService.findByGenresContaining(FIRST_GENRE_ID);
//        assertThat(actual).isEqualTo(expected);
//    }
//}