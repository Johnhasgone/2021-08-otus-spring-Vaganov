package ru.otus.spring18homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import ru.otus.spring18homework.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    Book findBookById(Long id);

    List<Book> findByTitle(String name);
}
