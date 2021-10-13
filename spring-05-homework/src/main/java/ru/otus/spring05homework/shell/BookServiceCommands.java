package ru.otus.spring05homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring05homework.domain.Author;
import ru.otus.spring05homework.domain.Book;
import ru.otus.spring05homework.domain.Genre;
import ru.otus.spring05homework.service.AuthorService;
import ru.otus.spring05homework.service.BookService;
import ru.otus.spring05homework.service.GenreService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookServiceCommands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @ShellMethod(value = "creating book", key = {"book-create"})
    public String createBook(String name, String genreName, String authorName) {
        Genre genre = getGenre(genreName);
        Author author = getAuthor(authorName);
        Book book = new Book(name, genre, author);
        return  "created book with id = " + bookService.create(book);
    }

    @ShellMethod(value = "getting book by id", key = {"book-get"})
    public String getBookById(Long id) {
        return bookService.getById(id).toString();
    }

    @ShellMethod(value = "getting all books", key = {"book-get-all"})
    public String getAllBooks() {
        return bookService.getAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "updating book", key = {"book-update"})
    public String updateBook(Long id, String name, String authorName, String genreName) {
        Genre genre = getGenre(genreName);
        Author author = getAuthor(authorName);
        Book book = new Book(id, name, genre, author);
        bookService.update(book);
        return "book updated";
    }

    private Genre getGenre(String genreName) {
        Genre genre = genreService.getByName(genreName);
        if (genre == null) {
            genre = genreService.getById(genreService.create(new Genre(genreName)));
        } return genre;
    }

    private Author getAuthor(String authorName) {
        Author author = authorService.getByName(authorName);
        if (author == null) {
            author = authorService.getById(authorService.create(new Author(authorName)));
        }
        return author;
    }

    @ShellMethod(value = "deleting book", key = {"book-delete"})
    public String deleteBook(Long id) {
        bookService.deleteById(id);
        return "book deleted";
    }

    @ShellMethod(value = "getting author by id", key = {"author-get"})
    public String getAuthorById(Long id) {
        return authorService.getById(id).toString();
    }

    @ShellMethod(value = "getting all authors", key = {"author-get-all"})
    public String getAllAuthors() {
        return authorService.getAll().stream().map(Author::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "creating author", key = {"author-create"})
    public String createAuthor(String name) {
        return "created author with id = " + authorService.create(new Author(name));
    }

    @ShellMethod(value = "updating author", key = {"author-update"})
    public String updateAuthor(Long id, String name) {
        authorService.update(new Author(id, name));
        return "author updated";
    }

    @ShellMethod(value = "deleting author", key = {"author-delete"})
    public String deleteAuthor(Long id) {
        authorService.deleteById(id);
        return "author deleted";
    }

    @ShellMethod(value = "getting genre by id", key = {"genre-get"})
    public String getGenreById(Long id) {
        return genreService.getById(id).toString();
    }

    @ShellMethod(value = "getting all genres", key = {"genre-get-all"})
    public String getAllGenres() {
        return genreService.getAll().stream().map(Genre::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "creating genre", key = {"genre-create"})
    public String createGenre(String name) {
        return "created genre with id = " + genreService.create(new Genre(name));
    }

    @ShellMethod(value = "updating genre", key = {"genre-update"})
    public String updateGenre(Long id, String name) {
        genreService.update(new Genre(id, name));
        return "genre updated";
    }

    @ShellMethod(value = "deleting genre", key = {"genre-delete"})
    public String deleteGenre(Long id) {
        genreService.deleteById(id);
        return "author deleted";
    }
}
