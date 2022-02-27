package ru.otus.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
