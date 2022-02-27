package ru.otus.trackservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.trackservice.domain.Track;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("select t from Track t join fetch t.genres genre where genre.id = :genreId")
    List<Track> findAllByGenre(@Param("genreId") Long genreId, Pageable pageable);
}
