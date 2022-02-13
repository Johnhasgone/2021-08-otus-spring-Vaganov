package ru.otus.spring18homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring18homework.dao.GenreRepository;
import ru.otus.spring18homework.domain.Genre;
import ru.otus.spring18homework.dto.GenreDto;
import ru.otus.spring18homework.mapper.GenreMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private static final String NO_INFO = "N/A";


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
    @HystrixCommand(fallbackMethod = "getGenreFallback")
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
    @HystrixCommand(fallbackMethod = "saveGenreFallback")
    public GenreDto save(String name) {
        Genre genre = new Genre(name);
        return GenreMapper.INSTANCE.toDto(genreRepository.save(genre));
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "deleteGenreFallback")
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return genreRepository.existsById(id);
    }

    private Optional<GenreDto> getGenreFallback(String name) {
        return Optional.of(new GenreDto(0L, NO_INFO));
    }

    private GenreDto saveGenreFallback(String name) {
        return new GenreDto(0L, NO_INFO);
    }


    private void deleteGenreFallback(Long id) {
        // nothing to do
    }
}
