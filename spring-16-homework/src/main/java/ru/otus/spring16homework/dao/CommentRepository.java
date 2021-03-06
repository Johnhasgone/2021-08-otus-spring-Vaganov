package ru.otus.spring16homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring16homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
}
