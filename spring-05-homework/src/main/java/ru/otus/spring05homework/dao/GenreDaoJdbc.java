package ru.otus.spring05homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring05homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into genre(name) values(:name)", Map.of("name", genre.getName()));
    }

    @Override
    public Genre getById(Long id) {
        return jdbc.queryForObject("select * from genre where id = :id", Map.of("id", id), new GenreRowMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genre", new GenreRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from genre where id = :id", Map.of("id", id));
    }

    public static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
