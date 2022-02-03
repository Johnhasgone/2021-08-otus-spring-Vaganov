package ru.otus.spring17homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring17homework.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByName(String name);
}
