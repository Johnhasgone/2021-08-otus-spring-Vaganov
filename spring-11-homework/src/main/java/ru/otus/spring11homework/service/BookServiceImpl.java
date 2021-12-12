package ru.otus.spring11homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring11homework.dao.BookRepository;
import ru.otus.spring11homework.dao.CommentRepository;
import ru.otus.spring11homework.domain.Author;
import ru.otus.spring11homework.domain.Book;
import ru.otus.spring11homework.domain.Genre;
import ru.otus.spring11homework.dto.BookDto;
import ru.otus.spring11homework.mapper.AuthorMapper;
import ru.otus.spring11homework.mapper.BookMapper;
import ru.otus.spring11homework.mapper.GenreMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(String id) {
        return Optional.ofNullable(BookMapper.INSTANCE.toDto(bookRepository.findById(id).orElse(null)));
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
    public BookDto save(String title, List<String> authorNames, List<String> genreNames) {
        List<Author> authors = getAuthors(authorNames);
        List<Genre> genres = getGenres(genreNames);
        Book book = new Book(null, title, authors, genres);
        return BookMapper.INSTANCE.toDto(bookRepository.save(book));
    }


    private List<Genre> getGenres(List<String> genreNames) {
        List<Genre> genres = new ArrayList<>();
        for (String genreName : genreNames) {
            Genre genre = GenreMapper.INSTANCE.toEntity(
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
            Author author = AuthorMapper.INSTANCE.toEntity(
                    authorService.findByName(authorName)
                            .orElseGet(() -> authorService.save(authorName))
            );
            authors.add(author);
        }
        return authors;
    }

    @Override
    @Transactional
    public void updateNameById(String id, String title) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(title);
            bookRepository.save(book);
        }
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteAll(commentRepository.findByBookId(id));
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return bookRepository.existsById(id);
    }

    @Override
    public List<BookDto> findByAuthorsContaining(String id) {
        Author author = AuthorMapper.INSTANCE.toEntity(authorService.findById(id).orElse(null));
        return bookRepository.findByAuthorsContaining(author)
                .stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findByGenresContaining(String id) {
        Genre genre = GenreMapper.INSTANCE.toEntity(genreService.findById(id).orElse(null));
        return bookRepository.findByGenresContaining(genre)
                .stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
