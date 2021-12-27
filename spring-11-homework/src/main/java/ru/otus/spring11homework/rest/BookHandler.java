package ru.otus.spring11homework.rest;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring11homework.dao.AuthorRepository;
import ru.otus.spring11homework.dao.BookRepository;
import ru.otus.spring11homework.dao.CommentRepository;
import ru.otus.spring11homework.dao.GenreRepository;
import ru.otus.spring11homework.domain.Author;
import ru.otus.spring11homework.domain.Book;
import ru.otus.spring11homework.domain.Genre;
import ru.otus.spring11homework.dto.BookDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookHandler {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    BookHandler(BookRepository bookRepository,
                AuthorRepository authorRepository,
                GenreRepository genreRepository,
                CommentRepository commentRepository) {
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

    public Mono<Book> save(BookDto bookDto) {
        return Mono.zip(getAuthors(bookDto.getAuthors()), getGenres(bookDto.getGenres()))
                .map(t -> new Book(bookDto.getId(), bookDto.getTitle(), t.getT1(), t.getT2()))
                .flatMap(bookRepository::save);
    }

    public Mono<Void> delete(String id) {
        return commentRepository.deleteByBookId(id)
                .then(bookRepository.deleteById(id));
    }
}
