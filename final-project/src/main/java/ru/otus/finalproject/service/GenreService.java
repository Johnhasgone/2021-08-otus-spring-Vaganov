package ru.otus.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.otus.finalproject.domain.Genre;
import ru.otus.finalproject.dto.GenreDto;
import ru.otus.finalproject.mapper.GenreMapper;
import ru.otus.finalproject.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreMapper genreMapper;
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreMapper genreMapper, GenreRepository genreRepository) {
        this.genreMapper = genreMapper;
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> findAll() {
        return genreRepository.findAll(Sort.by("name"))
                .stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    public GenreDto createGenre(String name) {
        return genreMapper.toDto(genreRepository.save(new Genre(name)));
    }
}
