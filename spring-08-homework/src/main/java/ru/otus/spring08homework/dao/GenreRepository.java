package ru.otus.spring08homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring08homework.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByName(String name);
}
