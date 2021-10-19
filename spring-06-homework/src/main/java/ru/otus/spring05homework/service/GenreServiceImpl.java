package ru.otus.spring05homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring05homework.dao.GenreDao;
import ru.otus.spring05homework.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Optional<Genre> findById(Long id) {
        return genreDao.findById(id);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        List<Genre> genres = genreDao.findByName(name);
        return Optional.ofNullable(genres.isEmpty() ? null : genres.get(0));
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

    @Override
    public Genre save(Genre genre) {
        return genreDao.save(genre);
    }

    @Override
    public boolean updateNameById(Long id, String name) {
        return genreDao.updateNameById(id, name) != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return genreDao.deleteById(id) != 0;
    }
}
