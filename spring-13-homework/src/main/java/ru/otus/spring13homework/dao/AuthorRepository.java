package ru.otus.spring13homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring13homework.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByName(String name);
}
