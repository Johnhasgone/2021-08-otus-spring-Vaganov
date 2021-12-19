package ru.otus.spring11homework;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring11homework.dao.AuthorRepository;
import ru.otus.spring11homework.dao.BookRepository;
import ru.otus.spring11homework.dao.CommentRepository;
import ru.otus.spring11homework.dao.GenreRepository;
import ru.otus.spring11homework.domain.Author;
import ru.otus.spring11homework.domain.Book;
import ru.otus.spring11homework.domain.Comment;
import ru.otus.spring11homework.domain.Genre;
import ru.otus.spring11homework.dto.BookDto;
import ru.otus.spring11homework.dto.CommentDto;

import java.util.List;

@SpringBootTest
class Spring11HomeworkApplicationTests {

    private final Author authorExpected = new Author("1", "author");

    private final Genre genreExpected = new Genre("1", "genre");

    private final Book bookExpected = new Book(
            "1",
            "title",
            List.of(authorExpected),
            List.of(genreExpected)
    );

    private final BookDto bookDtoExpected = new BookDto(
            bookExpected.getId(),
            bookExpected.getTitle(),
            bookExpected.getAuthors().get(0).getName(),
            bookExpected.getGenres().get(0).getName()
    );

    private final Comment commentExpected = new Comment("1", "text", bookExpected);

    private final CommentDto commentDtoExpected = new CommentDto("1", "text");

    @Autowired
    private RouterFunction<ServerResponse> composedRoutes;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private CommentRepository commentRepository;


    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAllBooks() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(composedRoutes)
                .build();

        Mockito.when(bookRepository.findAll()).thenReturn(Flux.just(bookExpected));

        client.get()
                .uri("/rest/book")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookDto.class)
                .isEqualTo(List.of(bookDtoExpected));
    }

    @Test
    public void testGetBookById() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(composedRoutes)
                .build();

        Mockito.when(bookRepository.findById(Mockito.anyString())).thenReturn(Mono.just(bookExpected));

        client.get()
                .uri("/rest/book/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(bookDtoExpected);
    }

    @Test
    public void testDeleteBookById() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(composedRoutes)
                .build();

        Mockito.when(bookRepository.deleteById(Mockito.anyString())).thenReturn(Mono.empty());
        Mockito.when(commentRepository.deleteByBookId(Mockito.anyString())).thenReturn(Mono.empty());

        client.delete()
                .uri("/rest/book/1")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testSaveBook() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(composedRoutes)
                .build();

        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(Mono.just(bookExpected));
        Mockito.when(authorRepository.findByName(Mockito.anyString())).thenReturn(Flux.just(authorExpected));
        Mockito.when(genreRepository.findByName(Mockito.anyString())).thenReturn(Flux.just(genreExpected));

        client.post()
                .uri("/rest/book")
                .body(Mono.just(bookDtoExpected), BookDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(bookDtoExpected);
    }

    @Test
    public void testSaveComment() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(composedRoutes)
                .build();

        Mockito.when(commentRepository.save(Mockito.any())).thenReturn(Mono.just(commentExpected));
        Mockito.when(bookRepository.findById(Mockito.anyString())).thenReturn(Mono.just(bookExpected));

        client.post()
                .uri("/rest/book/1/comment")
                .body(Mono.just(commentDtoExpected), CommentDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CommentDto.class)
                .isEqualTo(commentDtoExpected);
    }

    @Test
    public void testGetCommentsByBookId() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(composedRoutes)
                .build();

        Mockito.when(commentRepository.findByBookId(Mockito.anyString())).thenReturn(Flux.just(commentExpected));

        client.get()
                .uri("/rest/book/1/comment")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CommentDto.class)
                .isEqualTo(List.of(commentDtoExpected));
    }
}
