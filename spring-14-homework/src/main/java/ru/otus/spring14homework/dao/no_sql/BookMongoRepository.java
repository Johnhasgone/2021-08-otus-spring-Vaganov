package ru.otus.spring14homework.dao.no_sql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring14homework.domain.no_sql.Author;
import ru.otus.spring14homework.domain.no_sql.Book;
import ru.otus.spring14homework.domain.no_sql.Genre;


import java.util.List;

public interface BookMongoRepository extends MongoRepository<Book, String> {
    List<Book> findByTitle(String name);
    List<Book> findByAuthorsContaining(Author author);
    List<Book> findByGenresContaining(Genre genre);
}
