package ru.otus.spring14homework.dao.no_sql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring14homework.domain.no_sql.MongoGenre;

public interface MongoGenreRepository extends MongoRepository<MongoGenre, String> {
}
