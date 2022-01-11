package ru.otus.spring14homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring14homework.domain.sql.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
