package ru.otus.spring08homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring08homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
}
