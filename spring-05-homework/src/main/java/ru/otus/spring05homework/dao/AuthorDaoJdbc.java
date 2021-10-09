package ru.otus.spring05homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring05homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public void insert(Author author) {
        jdbc.update("insert into author(name) values(:name)",
                Map.of("name", author.getName()));
    }

    @Override
    public Author getById(Long id) {
        return jdbc.queryForObject("select * from author where id = :id", Map.of("id", id), new AuthorRowMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from author", new AuthorRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from author where id = :id", Map.of("id", id));
    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Author(id, name);
        }
    }
}
