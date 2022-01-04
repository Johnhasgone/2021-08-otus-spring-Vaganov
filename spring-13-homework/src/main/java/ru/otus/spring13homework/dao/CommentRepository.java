package ru.otus.spring13homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring13homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
}
