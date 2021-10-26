package ru.otus.spring07homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring07homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByName(String name);
    int updateNameById(Long id, String name);
    int deleteById(Long id);
}
