package ru.otus.spring10homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring10homework.dao.BookRepository;
import ru.otus.spring10homework.domain.Author;
import ru.otus.spring10homework.domain.Book;
import ru.otus.spring10homework.domain.Genre;
import ru.otus.spring10homework.dto.BookDto;
import ru.otus.spring10homework.mapper.AuthorMapper;
import ru.otus.spring10homework.mapper.BookMapper;
import ru.otus.spring10homework.mapper.GenreMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(Long id) {
        return BookMapper.INSTANCE.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + id, 1)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findByTitle(String title) {
        return bookRepository.findByTitle(title).stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        List<Author> authors = getAuthors(bookDto.getAuthors());
        List<Genre> genres = getGenres(bookDto.getGenres());
        Book book = new Book(bookDto.getId(), bookDto.getTitle(), authors, genres);
        return BookMapper.INSTANCE.toDto(bookRepository.save(book));
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
            Genre genre = GenreMapper.INSTANCE.toEntity(
                    genreService.findByName(genreName)
                            .orElseGet(() -> genreService.save(genreName))
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
            Author author = AuthorMapper.INSTANCE.toEntity(
                    authorService.findByName(authorName)
                            .orElseGet(() -> authorService.save(authorName))
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
