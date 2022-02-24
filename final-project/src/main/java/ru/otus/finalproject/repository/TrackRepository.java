package ru.otus.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
