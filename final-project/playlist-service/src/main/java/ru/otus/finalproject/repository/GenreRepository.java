package ru.otus.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
