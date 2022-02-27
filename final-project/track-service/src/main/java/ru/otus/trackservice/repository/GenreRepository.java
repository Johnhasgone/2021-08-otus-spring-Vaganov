package ru.otus.trackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.trackservice.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
