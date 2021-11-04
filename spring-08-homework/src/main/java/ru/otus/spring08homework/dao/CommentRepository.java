package ru.otus.spring08homework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring08homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);

    @Modifying
    @Query("update Comment c set c.text = :text where c.id = :id")
    void updateTextById(@Param("id") Long id, @Param("text") String text);
}
