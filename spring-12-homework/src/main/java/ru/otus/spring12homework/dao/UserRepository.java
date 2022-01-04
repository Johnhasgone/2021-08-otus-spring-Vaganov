package ru.otus.spring12homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring12homework.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
