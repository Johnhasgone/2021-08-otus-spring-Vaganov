package ru.otus.spring08homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring08homework.domain.Author;
import ru.otus.spring08homework.domain.Book;
import ru.otus.spring08homework.domain.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitle(String name);
    List<Book> findByAuthorsContaining(Author author);
    List<Book> findByGenresContaining(Genre genre);
}
