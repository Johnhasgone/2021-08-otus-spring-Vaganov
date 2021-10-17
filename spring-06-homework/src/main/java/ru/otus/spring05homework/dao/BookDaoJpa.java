package ru.otus.spring05homework.dao;

import liquibase.pro.packaged.B;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring05homework.domain.Author;
import ru.otus.spring05homework.domain.Book;
import ru.otus.spring05homework.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao {

    private final EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public int update(Book book) {
        Query query = em.createQuery("update Book b set b.title = :title, b.author = :author, b.genre = :genre, b.comments = :comments");
        query.setParameter("title", book.getTitle());
        query.setParameter("author", book.getAuthor());
        query.setParameter("genre", book.getGenre());
        query.setParameter("comments", book.getComments());
        return query.executeUpdate();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public boolean deleteById(Long id) {
        int res = jdbc.update("delete from book where id = :id", Map.of("id", id));
        return res != 0;
    }

    public static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("book_id");
            String title = rs.getString("book_title");
            Long authorId = rs.getLong("author_id");
            Long genreId = rs.getLong("genre_id");
            Long commentId = rs.getLong("comment_id");
            String authorName = rs.getString("author_name");
            String genreName = rs.getString("genre_name");
            String comment = rs.getString("comment");

            return new Book(id, title, new Genre(genreId, genreName), new Author(authorId, authorName));
        }
    }
}
