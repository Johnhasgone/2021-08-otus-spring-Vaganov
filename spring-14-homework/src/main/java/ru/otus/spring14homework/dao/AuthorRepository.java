package ru.otus.spring14homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring14homework.domain.sql.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
