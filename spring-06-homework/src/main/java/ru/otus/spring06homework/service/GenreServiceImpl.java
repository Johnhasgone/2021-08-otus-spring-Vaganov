package ru.otus.spring06homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring06homework.dao.GenreDao;
import ru.otus.spring06homework.domain.Genre;
import ru.otus.spring06homework.dto.GenreDto;
import ru.otus.spring06homework.mapper.GenreMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final GenreMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findById(Long id) {
        return Optional.ofNullable(
                mapper.toDto(
                        genreDao.findById(id).orElse(null)
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findByName(String name) {
        List<Genre> genres = genreDao.findByName(name);
        return Optional.ofNullable(genres.isEmpty() ? null : mapper.toDto(genres.get(0)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreDao.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GenreDto save(String name) {
        Genre genre = new Genre(name);
        return mapper.toDto(genreDao.save(genre));
    }

    @Override
    @Transactional
    public boolean updateNameById(Long id, String name) {
        return genreDao.updateNameById(id, name) != 0;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return genreDao.deleteById(id) != 0;
    }
}
