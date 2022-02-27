package ru.otus.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
