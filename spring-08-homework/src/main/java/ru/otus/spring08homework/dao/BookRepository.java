package ru.otus.spring08homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring08homework.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String name);

    @Modifying
    @Query("update Book b set b.title = :title where b.id = :id")
    void updateNameById(@Param("id") Long id, @Param("title") String title);
}
