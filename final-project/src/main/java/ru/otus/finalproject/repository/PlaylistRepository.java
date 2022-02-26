package ru.otus.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
