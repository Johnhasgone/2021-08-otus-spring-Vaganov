package ru.otus.spring08homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring08homework.dao.BookRepository;
import ru.otus.spring08homework.dao.GenreRepository;
import ru.otus.spring08homework.domain.Genre;
import ru.otus.spring08homework.dto.GenreDto;
import ru.otus.spring08homework.mapper.GenreMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final GenreMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findById(String id) {
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
    public void updateNameById(String id, String name) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();
            genre.setName(name);
            genreRepository.save(genre);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        Genre genre = genreRepository.findById(id).orElse(null);
        if (genre == null) {
            return false;
        }
        if (!bookRepository.findByGenresContaining(genre).isEmpty()) {
            return false;
        }
        genreRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return genreRepository.existsById(id);
    }
}
