package ru.otus.spring07homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring07homework.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByName(String name);
    @Query("update Author a set a.name = :name where a.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);
}
