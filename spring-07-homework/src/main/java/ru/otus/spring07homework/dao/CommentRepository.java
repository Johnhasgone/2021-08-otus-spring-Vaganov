package ru.otus.spring07homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring07homework.domain.Book;
import ru.otus.spring07homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook(Book book);
    @Query("update Comment c set c.text = :text where c.id = :id")
    void updateTextById(@Param("id") Long id, @Param("text") String text);
}
