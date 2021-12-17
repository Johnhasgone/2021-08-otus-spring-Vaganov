package ru.otus.spring10homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring10homework.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String name);
}
