package ru.otus.spring09homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring09homework.dao.BookRepository;
import ru.otus.spring09homework.domain.Author;
import ru.otus.spring09homework.domain.Book;
import ru.otus.spring09homework.domain.Genre;
import ru.otus.spring09homework.dto.BookDto;
import ru.otus.spring09homework.mapper.AuthorMapper;
import ru.otus.spring09homework.mapper.BookMapper;
import ru.otus.spring09homework.mapper.GenreMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final GenreMapper genreMapper;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(Long id) {
        return Optional.ofNullable(mapper.toDto(bookRepository.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findByTitle(String title) {
        return bookRepository.findByTitle(title).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        List<Author> authors = getAuthors(bookDto.getAuthors());
        List<Genre> genres = getGenres(bookDto.getGenres());
        Book book = new Book(bookDto.getId(), bookDto.getTitle(), authors, genres);
        return mapper.toDto(bookRepository.save(book));
    }


    private List<Genre> getGenres(String genres) {
        if (genres == null) {
            return List.of();
        }
        List<String> genreNames = Arrays.stream(genres.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        List<Genre> genreList = new ArrayList<>();
        for (String genreName : genreNames) {
            Genre genre = genreMapper.toEntity(
                    genreService.findByName(genreName)
                            .orElse(genreService.save(genreName))
            );
            genreList.add(genre);
        }
        return genreList;
    }

    private List<Author> getAuthors(String authors) {
        if (authors == null) {
            return List.of();
        }
        List<String> authorNames = Arrays.stream(authors.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        List<Author> authorList = new ArrayList<>();
        for (String authorName : authorNames) {
            Author author = authorMapper.toEntity(
                    authorService.findByName(authorName)
                            .orElse(authorService.save(authorName))
            );
            authorList.add(author);
        }
        return authorList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }
}
