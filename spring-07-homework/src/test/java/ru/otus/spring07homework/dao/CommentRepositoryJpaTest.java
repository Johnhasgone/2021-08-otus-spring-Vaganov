package ru.otus.spring07homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring07homework.domain.Book;
import ru.otus.spring07homework.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с жанрами книг должно ")
@DataJpaTest
class CommentRepositoryJpaTest {
    private static final Long FIRST_BOOK_ID = 1L;

    private static final Long FIRST_COMMENT_ID = 1L;
    private static final Long SECOND_COMMENT_ID = 2L;
    private static final Long THIRD_COMMENT_ID = 3L;
    private static final Long FORTH_COMMENT_ID = 4L;
    private static final Long FIFTH_COMMENT_ID = 5L;
    private static final Long SIXTH_COMMENT_ID = 6L;
    private static final Long SEVENTH_COMMENT_ID = 7L;

    private static final String CREATED_COMMENT_TEXT = "Отличная книга";
    private static final String EXPECTED_COMMENT_TEXT = "Замечательная книгга";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        Comment expectedComment = new Comment(null, CREATED_COMMENT_TEXT, book);
        commentRepository.save(expectedComment);
        Comment actualComment = em.find(Comment.class, expectedComment.getId());
        assertThat(actualComment.getId()).isNotNull().isGreaterThan(0);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("обновлять комментарий в БД")
    @Test
    void shouldUpdateComment() {
        Comment updatingComment = commentRepository.findAll().get(0);
        commentRepository.updateTextById(updatingComment.getId(), EXPECTED_COMMENT_TEXT);
        Comment actualComment = em.find(Comment.class, updatingComment.getId());
        assertThat(actualComment.getText()).isEqualTo(EXPECTED_COMMENT_TEXT);
    }

    @DisplayName("получать комментарий по ID")
    @Test
    void shouldGetExpectedCommentById() {
        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        Optional<Comment> actualComment = commentRepository.findById(FIRST_COMMENT_ID);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("получать комментарии по книге")
    @Test
    void shouldGetExpectedCommentByBook() {
        List<Comment> expectedComments = List.of(
                em.find(Comment.class, FIRST_COMMENT_ID),
                em.find(Comment.class, SECOND_COMMENT_ID),
                em.find(Comment.class, THIRD_COMMENT_ID)
        );
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        List<Comment> actualComments = commentRepository.findByBook(book);
        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(expectedComments);
    }

    @DisplayName("получать все комментарии из БД")
    @Test
    void shouldGetAllComments() {
        List<Comment> expectedComments = List.of(
                em.find(Comment.class, FIRST_COMMENT_ID),
                em.find(Comment.class, SECOND_COMMENT_ID),
                em.find(Comment.class, THIRD_COMMENT_ID),
                em.find(Comment.class, FORTH_COMMENT_ID),
                em.find(Comment.class, FIFTH_COMMENT_ID),
                em.find(Comment.class, SIXTH_COMMENT_ID),
                em.find(Comment.class, SEVENTH_COMMENT_ID)

        );
        List<Comment> actualComments = commentRepository.findAll();
        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(expectedComments);
    }

    @DisplayName("удалять комментарий по ID")
    @Test
    void shouldDeleteCommentById() {
        Comment deletingComment = em.find(Comment.class, SEVENTH_COMMENT_ID);
        assertThat(deletingComment).isNotNull();
        commentRepository.deleteById(SEVENTH_COMMENT_ID);
        Comment deletedComment = em.find(Comment.class, SEVENTH_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }
}