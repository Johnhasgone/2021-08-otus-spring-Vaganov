package ru.otus.spring14homework.dao.no_sql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring14homework.domain.no_sql.MongoAuthor;


public interface MongoAuthorRepository extends MongoRepository<MongoAuthor, String> {
}
