package ru.otus.spring08homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring08homework.domain.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    List<Author> findByName(String name);

}
