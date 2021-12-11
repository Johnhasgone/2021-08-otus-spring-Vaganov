package ru.otus.spring06homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring06homework.dao.BookDao;
import ru.otus.spring06homework.domain.Author;
import ru.otus.spring06homework.domain.Book;
import ru.otus.spring06homework.domain.Genre;
import ru.otus.spring06homework.dto.BookDto;
import ru.otus.spring06homework.mapper.AuthorMapper;
import ru.otus.spring06homework.mapper.BookMapper;
import ru.otus.spring06homework.mapper.GenreMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final BookMapper mapper;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final GenreMapper genreMapper;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(Long id) {
        return Optional.ofNullable(mapper.toDto(bookDao.findById(id).orElse(null)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findByTitle(String title) {
        return bookDao.findByTitle(title).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookDao.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto save(String title, List<String> authorNames, List<String> genreNames) {
        List<Author> authors = getAuthors(authorNames);
        List<Genre> genres = getGenres(genreNames);
        Book book = new Book(null, title, authors, genres);
        return mapper.toDto(bookDao.save(book));
    }


    private List<Genre> getGenres(List<String> genreNames) {
        List<Genre> genres = new ArrayList<>();
        for (String genreName : genreNames) {
            Genre genre = genreMapper.toEntity(
                    genreService.findByName(genreName)
                            .orElseGet(() -> genreService.save(genreName))
            );
            genres.add(genre);
        }
        return genres;
    }

    private List<Author> getAuthors(List<String> authorNames) {
        List<Author> authors = new ArrayList<>();
        for (String authorName : authorNames) {
            Author author = authorMapper.toEntity(
                    authorService.findByName(authorName)
                            .orElseGet(() -> authorService.save(authorName))
            );
            authors.add(author);
        }
        return authors;
    }

    @Override
    @Transactional
    public boolean updateNameById(Long id, String title) {
        return bookDao.updateNameById(id, title) != 0;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return bookDao.deleteById(id) != 0;
    }
}
