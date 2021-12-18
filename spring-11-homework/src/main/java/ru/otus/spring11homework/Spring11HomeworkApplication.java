package ru.otus.spring11homework;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
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
import ru.otus.spring11homework.mapper.BookMapper;
import ru.otus.spring11homework.mapper.CommentMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Spring11HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring11HomeworkApplication.class, args);
    }


    @Bean
    public RouterFunction<ServerResponse> htmlRouter(
            @Value("classpath:/templates/bookMain.html") Resource html) {
        return route(GET("/"), request
                -> ok().contentType(MediaType.TEXT_HTML).bodyValue(html)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookRepository bookRepository,
                                                         CommentRepository commentRepository,
                                                         BookHandler bookHandler,
                                                         CommentHandler commentHandler) {
        return route()
                .GET("/rest/book", accept(APPLICATION_JSON),
                        req -> bookRepository.findAll()
                                .map(BookMapper.INSTANCE::toDto)
                                .collectList()
                                .flatMap(bookDtos -> ok().bodyValue(bookDtos))
                ).GET("/rest/book/{id}", accept(APPLICATION_JSON),
                        req -> bookRepository.findById(req.pathVariable("id"))
                                .map(BookMapper.INSTANCE::toDto)
                                .flatMap(bookDto -> ok().contentType(APPLICATION_JSON).body(fromValue(bookDto)))
                ).DELETE("/rest/book/{id}", accept(APPLICATION_JSON),
                        req -> bookHandler.delete(req.pathVariable("id"))
                                .then(ok().build())
                ).POST("/rest/book", accept(APPLICATION_JSON),
                        req -> req.bodyToMono(BookDto.class)
                                .flatMap(bookHandler::save)
                                .map(BookMapper.INSTANCE::toDto)
                                .flatMap(bookDto -> ok().contentType(APPLICATION_JSON).body(fromValue(bookDto)))
                ).POST("/rest/book/{id}/comment", accept(APPLICATION_JSON),
                        req -> commentHandler.save(req)
                                .map(CommentMapper.INSTANCE::toDto)
                                .flatMap(commentDto -> ok().contentType(APPLICATION_JSON).body(fromValue(commentDto)))
                ).GET("/rest/book/{id}/comment", accept(APPLICATION_JSON),
                        req -> commentRepository.findByBookId(req.pathVariable("id"))
                                .map(CommentMapper.INSTANCE::toDto)
                                .collectList()
                                .flatMap(commentDtos -> ok().bodyValue(commentDtos))
                ).build();
    }

    @Component
    static class BookHandler {

        private final BookRepository bookRepository;
        private final AuthorRepository authorRepository;
        private final GenreRepository genreRepository;
        private final CommentRepository commentRepository;

        BookHandler(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
            this.bookRepository = bookRepository;
            this.authorRepository = authorRepository;
            this.genreRepository = genreRepository;
            this.commentRepository = commentRepository;
        }

        private Mono<List<Genre>> getGenres(String genres) {
            if (genres == null) {
                return Mono.just(List.of());
            }
            List<String> genreNames = Arrays.stream(genres.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            return Flux.fromIterable(genreNames)
                    .flatMap(name -> genreRepository.findByName(name)
                            .collectList()
                            .flatMap(list -> list.isEmpty()
                                    ? genreRepository.save(new Genre(null, name))
                                    : Mono.just(list.get(0))
                            )
                    ).collectList();
        }

        private Mono<List<Author>> getAuthors(String authors) {
            if (authors == null) {
                return Mono.just(List.of());
            }
            List<String> authorNames = Arrays.stream(authors.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            return Flux.fromIterable(authorNames)
                    .flatMap(name -> authorRepository.findByName(name)
                            .collectList()
                            .flatMap(list -> list.isEmpty()
                                    ? authorRepository.save(new Author(null, name))
                                    : Mono.just(list.get(0))
                            )
                    ).collectList();
        }

        private Mono<Book> save(BookDto bookDto) {
            return Mono.zip(
                            List.of(Mono.justOrEmpty(bookDto.getId()).defaultIfEmpty(new ObjectId().toString()),
                                    Mono.just(bookDto.getTitle()),
                                    getAuthors(bookDto.getAuthors()),
                                    getGenres(bookDto.getGenres())
                            ), array -> new Book((String) array[0], (String) array[1], (List<Author>) array[2], (List<Genre>) array[3]))
                    .flatMap(bookRepository::save);
        }

        private Mono<Void> delete(String id) {
            return commentRepository.deleteByBookId(id)
                    .then(bookRepository.deleteById(id));
        }
    }

    @Component
    static class CommentHandler {
        private final CommentRepository commentRepository;
        private final BookRepository bookRepository;

        public CommentHandler(CommentRepository commentRepository, BookRepository bookRepository) {
            this.commentRepository = commentRepository;
            this.bookRepository = bookRepository;
        }

        private Mono<Comment> save(ServerRequest request) {
            return Mono.zip(request.bodyToMono(CommentDto.class),
                            bookRepository.findById(request.pathVariable("id")),
                            (c, b) -> new Comment(c.getText(), b)
                    ).flatMap(commentRepository::save);
        }
    }
}
