package ru.otus.spring05homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring05homework.domain.Author;
import ru.otus.spring05homework.domain.Book;
import ru.otus.spring05homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into book(name, genre_id, author_id) values(:name, :genreId, :authorId)",
                Map.of( "name", book.getName(),
                        "genreId", book.getGenre().getId(),
                        "authorId", book.getAuthor().getId()));
    }

    @Override
    public Book getById(Long id) {
        return jdbc.queryForObject("select b.id as book_id, b.name as book_name, b.genre_id, b.author_id, a.name as author_name, g.name as genre_name " +
                "from book b " +
                "left join genre g on b.genre_id = g.id " +
                "left join author a on b.author_id = a.id " +
                "where b.id = :id",
                Map.of("id", id),
                new BookRowMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id as book_id, b.name as book_name, b.genre_id, b.author_id, a.name as author_name, g.name as genre_name " +
                "from book b " +
                "left join genre g on b.genre_id = g.id " +
                "left join author a on b.author_id = a.id", new BookRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    public static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("book_id");
            String name = rs.getString("book_name");
            Long authorId = rs.getLong("author_id");
            Long genreId = rs.getLong("genre_id");
            String authorName = rs.getString("author_name");
            String genreName = rs.getString("genre_name");
            return new Book(id, name, new Genre(genreId, genreName), new Author(authorId, authorName));
        }
    }
}
