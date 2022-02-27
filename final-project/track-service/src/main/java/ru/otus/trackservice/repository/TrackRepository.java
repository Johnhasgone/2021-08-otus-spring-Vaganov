package ru.otus.trackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.trackservice.domain.Track;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("select t from Track t join fetch t.genres genre where genre.id in :genreIds")
    List<Track> findAllByGenres(@Param("genreIds") List<Long> genreIds);
}
