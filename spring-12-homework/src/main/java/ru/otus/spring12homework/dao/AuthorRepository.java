package ru.otus.spring12homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring12homework.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByName(String name);
}
