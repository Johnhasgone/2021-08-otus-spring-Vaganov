package ru.otus.spring05homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    public Long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into author(name) values(:name)",
                params,
                keyHolder
        );
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public void update(Author author) {
        jdbc.update("update author set name = :name where id = :id",
                Map.of("name", author.getName(), "id", author.getId()));
    }

    @Override
    public Author getById(Long id) {
        List<Author> authors = jdbc.query("select * from author where id = :id", Map.of("id", id), new AuthorRowMapper());
        return authors.isEmpty() ? null : authors.get(0);
    }

    @Override
    public Author getByName(String name) {
        List<Author> authors = jdbc.query("select * from author where name = :name", Map.of("name", name), new AuthorRowMapper());
        return authors.isEmpty() ? null : authors.get(0);
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
