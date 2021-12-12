package ru.otus.spring11homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring11homework.domain.Author;
import ru.otus.spring11homework.domain.Book;
import ru.otus.spring11homework.domain.Genre;

import java.util.List;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByTitle(String name);
    Flux<Book> findByAuthorsContaining(Author author);
    Flux<Book> findByGenresContaining(Genre genre);
}
