package ru.otus.trackservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.otus.trackservice.domain.Genre;
import ru.otus.trackservice.dto.GenreDto;
import ru.otus.trackservice.mapper.GenreMapper;
import ru.otus.trackservice.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> findAll() {
        return genreRepository.findAll(Sort.by("name"))
                .stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public GenreDto createGenre(String name) {
        return GenreMapper.INSTANCE.toDto(
                genreRepository.save(new Genre(name))
        );
    }
}
