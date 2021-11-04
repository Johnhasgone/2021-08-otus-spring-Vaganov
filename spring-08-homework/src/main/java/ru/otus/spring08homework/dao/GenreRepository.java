package ru.otus.spring08homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring08homework.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByName(String name);

    @Modifying
    @Query("update Genre g set g.name = :name where g.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);
}
