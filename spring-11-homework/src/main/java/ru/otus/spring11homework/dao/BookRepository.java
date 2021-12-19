package ru.otus.spring11homework.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring11homework.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
