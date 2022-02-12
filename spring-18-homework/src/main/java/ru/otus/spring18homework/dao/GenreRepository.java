package ru.otus.spring18homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring18homework.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByName(String name);
}
