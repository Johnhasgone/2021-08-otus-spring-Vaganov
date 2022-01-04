package ru.otus.spring13homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring13homework.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
