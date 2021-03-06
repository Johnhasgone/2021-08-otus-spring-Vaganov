package ru.otus.spring11homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring11homework.domain.Author;

import java.util.List;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Flux<Author> findByName(String name);

}
