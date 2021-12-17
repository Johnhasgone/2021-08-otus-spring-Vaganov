package ru.otus.spring11homework;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
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

//    @Bean
//    public RouterFunction<ServerResponse> staticResources() {
//        return resources("/**", new ClassPathResource("/static/"));
//    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookRepository bookRepository,
                                                         AuthorRepository authorRepository,
                                                         GenreRepository genreRepository,
                                                         CommentRepository commentRepository) {
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
                        req -> {
                            bookRepository.deleteById(req.pathVariable("id"));
                            return ok().build();
                        }
                ).POST("/rest/book", accept(APPLICATION_JSON),
                        req -> req.bodyToMono(BookDto.class)
                                .map(BookMapper.INSTANCE::toEntity)
                                .flatMap(bookRepository::save)
                                .map(BookMapper.INSTANCE::toDto)
                                .flatMap(bookDto -> ok().contentType(APPLICATION_JSON).body(fromValue(bookDto)))
                ).POST("/rest/book/{id}/comment", accept(APPLICATION_JSON),
                        req -> req.bodyToMono(CommentDto.class)
                                .map(CommentMapper.INSTANCE::toEntity)
                                .flatMap(commentRepository::save)
                                .map(CommentMapper.INSTANCE::toDto)
                                .flatMap(commentDto -> ok().contentType(APPLICATION_JSON).body(fromValue(commentDto)))
                ).GET("/rest/book/{id}/comment", accept(APPLICATION_JSON),
                        req -> commentRepository.findByBookId(req.pathVariable("id"))
                                .map(CommentMapper.INSTANCE::toDto)
                                .collectList()
                                .flatMap(commentDtos -> ok().body(commentDtos, CommentDto.class))
                )
                .build();
    }

//    @Bean
//    public DownloadConfigBuilderCustomizer downloadConfigBuilderCustomizer() {
//        return (downloadConfigBuilder) -> downloadConfigBuilder
//                .downloadPath(distribution -> "https://fastdl.mongodb.org/macos/mongodb-macos-x86_64-5.0.4.tgz")
//                .build();
//    }
}
