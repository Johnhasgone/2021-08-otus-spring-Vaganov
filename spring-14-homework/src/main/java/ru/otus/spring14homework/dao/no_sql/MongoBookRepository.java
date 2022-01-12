package ru.otus.spring14homework.dao.no_sql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring14homework.domain.no_sql.MongoBook;

import java.util.List;

public interface MongoBookRepository extends MongoRepository<MongoBook, String> {
    List<MongoBook> findByTitle(String name);
}
