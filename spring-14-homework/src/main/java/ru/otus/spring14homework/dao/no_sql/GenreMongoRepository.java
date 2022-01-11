package ru.otus.spring14homework.dao.no_sql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring14homework.domain.no_sql.Genre;

import java.util.List;

public interface GenreMongoRepository extends MongoRepository<Genre, String> {
    List<Genre> findByName(String name);
}
