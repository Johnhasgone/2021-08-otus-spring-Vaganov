package ru.otus.spring11homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring11homework.dao.AuthorRepository;
import ru.otus.spring11homework.dao.BookRepository;
import ru.otus.spring11homework.dao.CommentRepository;
import ru.otus.spring11homework.dao.GenreRepository;
import ru.otus.spring11homework.dto.BookDto;
import ru.otus.spring11homework.dto.CommentDto;
import ru.otus.spring11homework.mapper.BookMapper;
import ru.otus.spring11homework.mapper.CommentMapper;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class Spring11HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring11HomeworkApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookRepository bookRepository,
                                                         AuthorRepository authorRepository,
                                                         GenreRepository genreRepository,
                                                         CommentRepository commentRepository) {
        return route()
                .GET("/book", accept(APPLICATION_JSON),
                        req -> bookRepository.findAll()
                                .map(BookMapper.INSTANCE::toDto)
                                .collectList()
                                .flatMap(bookDtos -> ok().body(bookDtos, BookDto.class))
                ).GET("/book/{id}", accept(APPLICATION_JSON),
                        req -> bookRepository.findById(req.pathVariable("id"))
                                .map(BookMapper.INSTANCE::toDto)
                                .flatMap(bookDto -> ok().contentType(APPLICATION_JSON).body(fromValue(bookDto)))
                ).DELETE("/book/{id}", accept(APPLICATION_JSON),
                        req -> {
                            bookRepository.deleteById(req.pathVariable("id"));
                            return ok().build();
                        }
                ).POST("/book", accept(APPLICATION_JSON),
                        req -> req.bodyToMono(BookDto.class)
                                .map(BookMapper.INSTANCE::toEntity)
                                .flatMap(bookRepository::save)
                                .map(BookMapper.INSTANCE::toDto)
                                .flatMap(bookDto -> ok().contentType(APPLICATION_JSON).body(fromValue(bookDto)))
                ).POST("/book/{id}/comment", accept(APPLICATION_JSON),
                        req -> req.bodyToMono(CommentDto.class)
                                .map(CommentMapper.INSTANCE::toEntity)
                                .flatMap(commentRepository::save)
                                .map(CommentMapper.INSTANCE::toDto)
                                .flatMap(commentDto -> ok().contentType(APPLICATION_JSON).body(fromValue(commentDto)))
                ).GET("/book/{id}/comment", accept(APPLICATION_JSON),
                        req -> commentRepository.findByBookId(req.pathVariable("id"))
                                .map(CommentMapper.INSTANCE::toDto)
                                .collectList()
                                .flatMap(commentDtos -> ok().body(commentDtos, CommentDto.class))
                )
                .build();
    }
}
