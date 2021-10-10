package ru.otus.spring05homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    public Long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genre(name) values(:name)", params, keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("update genre set name = :name where id = :id",
                Map.of("name", genre.getName(), "id", genre.getId()));
    }

    @Override
    public Genre getById(Long id) {
        List<Genre> genres = jdbc.query("select * from genre where id = :id",
                Map.of("id", id), new GenreRowMapper());
        return genres.isEmpty() ? null : genres.get(0);
    }

    @Override
    public Genre getByName(String name) {
        List<Genre> genres = jdbc.query("select * from genre where name = :name",
                Map.of("name", name), new GenreRowMapper());

        return genres.isEmpty() ? null : genres.get(0);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genre", new GenreRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from genre where id = :id",
                Map.of("id", id));
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
