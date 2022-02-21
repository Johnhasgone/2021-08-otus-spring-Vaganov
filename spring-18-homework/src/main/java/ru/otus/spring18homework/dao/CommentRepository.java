package ru.otus.spring18homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring18homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
}
