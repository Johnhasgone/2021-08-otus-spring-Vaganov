package ru.otus.spring05homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring05homework.dao.GenreDao;
import ru.otus.spring05homework.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao dao) {
        this.genreDao = dao;
    }

    @Override
    public Genre getById(Long id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre getByName(String name) {
        return genreDao.getByName(name);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Long create(Genre genre) {
        return genreDao.insert(genre);
    }

    @Override
    public void update(Genre genre) {
        genreDao.update(genre);
    }

    @Override
    public void deleteById(Long id) {
        genreDao.deleteById(id);
    }
}
