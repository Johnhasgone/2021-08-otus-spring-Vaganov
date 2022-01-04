package ru.otus.spring12homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring12homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
}
