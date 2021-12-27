package ru.otus.spring11homework.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import ru.otus.spring11homework.dao.BookRepository;
import ru.otus.spring11homework.dao.CommentRepository;
import ru.otus.spring11homework.domain.Comment;
import ru.otus.spring11homework.dto.CommentDto;

@Component
public class CommentHandler {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentHandler(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    public Mono<Comment> save(ServerRequest request) {
        return Mono.zip(request.bodyToMono(CommentDto.class),
                bookRepository.findById(request.pathVariable("id")),
                (c, b) -> new Comment(c.getText(), b)
        ).flatMap(commentRepository::save);
    }
}
