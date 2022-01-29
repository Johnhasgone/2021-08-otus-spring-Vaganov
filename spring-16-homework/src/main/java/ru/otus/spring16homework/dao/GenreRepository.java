package ru.otus.spring16homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring16homework.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByName(String name);
}
