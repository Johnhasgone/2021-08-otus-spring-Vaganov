package ru.otus.finalproject.repository;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
