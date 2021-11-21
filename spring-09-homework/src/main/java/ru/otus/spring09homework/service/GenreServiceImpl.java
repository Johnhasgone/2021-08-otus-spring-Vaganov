package ru.otus.spring09homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring09homework.dao.GenreRepository;
import ru.otus.spring09homework.domain.Genre;
import ru.otus.spring09homework.dto.GenreDto;
import ru.otus.spring09homework.mapper.GenreMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findById(Long id) {
        return Optional.ofNullable(
                mapper.toDto(
                        genreRepository.findById(id).orElse(null)
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findByName(String name) {
        List<Genre> genres = genreRepository.findByName(name);
        return Optional.ofNullable(genres.isEmpty() ? null : mapper.toDto(genres.get(0)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GenreDto save(String name) {
        Genre genre = new Genre(name);
        return mapper.toDto(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return genreRepository.existsById(id);
    }
}
