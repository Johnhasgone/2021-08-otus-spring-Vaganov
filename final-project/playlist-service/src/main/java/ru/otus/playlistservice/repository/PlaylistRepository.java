package ru.otus.playlistservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.playlistservice.domain.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
