package ru.otus.playlistservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.playlistservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
