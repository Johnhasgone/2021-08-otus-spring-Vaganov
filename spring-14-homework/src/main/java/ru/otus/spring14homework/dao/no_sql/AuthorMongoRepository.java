package ru.otus.spring14homework.dao.no_sql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring14homework.domain.no_sql.Author;

import java.util.List;

public interface AuthorMongoRepository extends MongoRepository<Author, String> {
    List<Author> findByName(String name);

}
