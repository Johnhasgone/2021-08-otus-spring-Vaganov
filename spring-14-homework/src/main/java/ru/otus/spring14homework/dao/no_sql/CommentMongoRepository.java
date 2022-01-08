package ru.otus.spring14homework.dao.no_sql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring14homework.domain.no_sql.Comment;

import java.util.List;

public interface CommentMongoRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
}
