package ru.otus.spring14homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring14homework.domain.sql.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
}
