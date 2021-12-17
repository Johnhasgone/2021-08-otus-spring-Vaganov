package ru.otus.spring10homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring10homework.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByName(String name);
}
