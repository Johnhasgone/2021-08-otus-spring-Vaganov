package ru.otus.spring08homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring08homework.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с жанрами книг должно ")
@DataJpaTest
class CommentRepositoryTest {
    private static final String FIRST_BOOK_ID = "1";

    private static final String FIRST_COMMENT_ID = "1";
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

    @DisplayName("получать комментарии по книге")
    @Test
    void shouldGetExpectedCommentByBookId() {
        List<Comment> expectedComments = List.of(
                em.find(Comment.class, FIRST_COMMENT_ID),
                em.find(Comment.class, SECOND_COMMENT_ID),
                em.find(Comment.class, THIRD_COMMENT_ID)
        );
        List<Comment> actualComments = commentRepository.findByBookId(FIRST_BOOK_ID);
        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(expectedComments);
    }
}