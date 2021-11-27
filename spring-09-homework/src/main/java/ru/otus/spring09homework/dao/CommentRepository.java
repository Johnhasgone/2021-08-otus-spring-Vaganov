package ru.otus.spring09homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring09homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
}
