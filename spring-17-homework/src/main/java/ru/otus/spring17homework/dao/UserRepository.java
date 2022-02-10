package ru.otus.spring17homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring17homework.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
