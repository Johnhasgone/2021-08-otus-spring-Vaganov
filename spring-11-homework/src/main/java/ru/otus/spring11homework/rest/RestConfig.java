package ru.otus.spring11homework.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring11homework.dao.BookRepository;
import ru.otus.spring11homework.dao.CommentRepository;
import ru.otus.spring11homework.dto.BookDto;
import ru.otus.spring11homework.mapper.BookMapper;
import ru.otus.spring11homework.mapper.CommentMapper;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RestConfig {

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
                                .flatMap(bookDtos -> ok().contentType(APPLICATION_JSON).bodyValue(bookDtos))
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
}
