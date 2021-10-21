package ru.otus.spring06homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring06homework.domain.Author;
import ru.otus.spring06homework.domain.Book;
import ru.otus.spring06homework.domain.Genre;
import ru.otus.spring06homework.service.AuthorService;
import ru.otus.spring06homework.service.BookService;
import ru.otus.spring06homework.service.GenreService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookServiceCommands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @ShellMethod(value = "creating book", key = {"book-create"})
    public String createBook(String title, String genreNames, String authorNames) {
        List<Author> authors = getAuthors(authorNames);
        List<Genre> genres = getGenres(genreNames);
        Book book = new Book(null, title, authors, genres);
        return  "создана книга с id = " + bookService.save(book).getId();
    }

    @ShellMethod(value = "getting book by id", key = {"book-get"})
    public String getBookById(Long id) {
        Optional<Book> book = bookService.findById(id);
        return book.isPresent() ? book.get().toString() : "книга не найдена";
    }

    @ShellMethod(value = "getting all books", key = {"book-get-all"})
    public String getAllBooks() {
        List<Book> books = bookService.findAll();

        return !books.isEmpty()
                ? books.stream()
                        .map(Book::toString)
                        .collect(Collectors.joining("\n"))
                : "книги не найдены";
    }

    @ShellMethod(value = "updating book", key = {"book-update"})
    public String updateBook(Long id, String title) {
        return bookService.updateNameById(id, title) ? "книга обновлена" : "книга не найдена";
    }

    private List<Genre> getGenres(String genreNames) {
        List<String> genreNameArray = Arrays.stream(genreNames.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for (String genreName : genreNameArray) {
            Genre genre = genreService.findByName(genreName).orElse(genreService.save(new Genre(genreName)));
            genres.add(genre);
        }
        return genres;
    }

    private List<Author> getAuthors(String authorNames) {
        List<String> authorNamesArray = Arrays.stream(authorNames.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        List<Author> authors = new ArrayList<>();
        for (String authorName : authorNamesArray) {
            Author author = authorService.findByName(authorName).orElse(authorService.save(new Author(authorName)));
            authors.add(author);
        }
        return authors;
    }

    @ShellMethod(value = "deleting book", key = {"book-delete"})
    public String deleteBook(Long id) {
        return bookService.deleteById(id) ? "книга удалена" : "книга не найдена";
    }

    @ShellMethod(value = "getting author by id", key = {"author-get"})
    public String getAuthorById(Long id) {
        Optional<Author> author = authorService.findById(id);
        return author.isPresent() ? author.get().toString() : "автор не найден";
    }

    @ShellMethod(value = "getting all authors", key = {"author-get-all"})
    public String getAllAuthors() {
        List<Author> authors = authorService.findAll();
        return !authors.isEmpty()
                ? authors.stream()
                        .map(Author::toString)
                        .collect(Collectors.joining("\n"))
                : "авторы не найдены";
    }

    @ShellMethod(value = "creating author", key = {"author-create"})
    public String createAuthor(String name) {
        return "создан автор с id = " + authorService.save(new Author(name));
    }

    @ShellMethod(value = "updating author", key = {"author-update"})
    public String updateAuthor(Long id, String name) {
        return authorService.updateNameById(id, name) ? "автор обновлен" : "автор не найден";
    }

    @ShellMethod(value = "deleting author", key = {"author-delete"})
    public String deleteAuthor(Long id) {
        return authorService.deleteById(id) ? "автор удален" : "автор не найден";
    }

    @ShellMethod(value = "getting genre by id", key = {"genre-get"})
    public String getGenreById(Long id) {
        Optional<Genre> genre = genreService.findById(id);
        return genre.isPresent() ? genre.get().toString() : "жанр не найден";
    }

    @ShellMethod(value = "getting all genres", key = {"genre-get-all"})
    public String getAllGenres() {
        List<Genre> genres = genreService.findAll();
        return !genres.isEmpty()
                ? genres.stream()
                        .map(Genre::toString)
                        .collect(Collectors.joining("\n"))
                : "жанры не найдены";
    }

    @ShellMethod(value = "creating genre", key = {"genre-create"})
    public String createGenre(String name) {
        return "создан жанр с id = " + genreService.save(new Genre(name));
    }

    @ShellMethod(value = "updating genre", key = {"genre-update"})
    public String updateGenre(Long id, String name) {
        return genreService.updateNameById(id, name) ? "жанр обновлен" : "жанр не найден";
    }

    @ShellMethod(value = "deleting genre", key = {"genre-delete"})
    public String deleteGenre(Long id) {
        return genreService.deleteById(id) ? "жанр удален" : "жанр не найден";
    }
}