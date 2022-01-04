package ru.otus.spring12homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring12homework.dao.GenreRepository;
import ru.otus.spring12homework.domain.Genre;
import ru.otus.spring12homework.dto.GenreDto;
import ru.otus.spring12homework.mapper.GenreMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findById(Long id) {
        return Optional.ofNullable(
                GenreMapper.INSTANCE.toDto(
                        genreRepository.findById(id).orElse(null)
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findByName(String name) {
        List<Genre> genres = genreRepository.findByName(name);
        return Optional.ofNullable(genres.isEmpty() ? null : GenreMapper.INSTANCE.toDto(genres.get(0)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GenreDto save(String name) {
        Genre genre = new Genre(name);
        return GenreMapper.INSTANCE.toDto(genreRepository.save(genre));
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
