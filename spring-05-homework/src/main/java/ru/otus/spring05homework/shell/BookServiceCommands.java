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

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookServiceCommands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @ShellMethod(value = "creating book", key = {"book-create"})
    public String createBook(String title, String genreName, String authorName) {
        Genre genre = getGenre(genreName);
        Author author = getAuthor(authorName);
        Book book = new Book(title, genre, author);
        return  "создана книга с id = " + bookService.create(book);
    }

    @ShellMethod(value = "getting book by id", key = {"book-get"})
    public String getBookById(Long id) {
        Book book = bookService.getById(id);
        return book != null ? book.toString() : "книга не найдена";
    }

    @ShellMethod(value = "getting all books", key = {"book-get-all"})
    public String getAllBooks() {
        List<Book> books = bookService.getAll();

        return !books.isEmpty()
                ? books.stream()
                        .map(Book::toString)
                        .collect(Collectors.joining("\n"))
                : "книги не найдены";
    }

    @ShellMethod(value = "updating book", key = {"book-update"})
    public String updateBook(Long id, String title, String authorName, String genreName) {
        Genre genre = getGenre(genreName);
        Author author = getAuthor(authorName);
        Book book = new Book(id, title, genre, author);
        return bookService.update(book) ? "книга обновлена" : "книга не найдена";
    }

    private Genre getGenre(String genreName) {
        Genre genre = genreService.getByName(genreName);
        if (genre == null) {
            genre = new Genre(genreService.create(new Genre(genreName)), genreName);
        }
        return genre;
    }

    private Author getAuthor(String authorName) {
        Author author = authorService.getByName(authorName);
        if (author == null) {
            author = new Author(authorService.create(new Author(authorName)), authorName);
        }
        return author;
    }

    @ShellMethod(value = "deleting book", key = {"book-delete"})
    public String deleteBook(Long id) {
        return bookService.deleteById(id) ? "книга удалена" : "книга не найдена";
    }

    @ShellMethod(value = "getting author by id", key = {"author-get"})
    public String getAuthorById(Long id) {
        Author author = authorService.getById(id);
        return author != null ? author.toString() : "автор не найден";
    }

    @ShellMethod(value = "getting all authors", key = {"author-get-all"})
    public String getAllAuthors() {
        List<Author> authors = authorService.getAll();
        return !authors.isEmpty()
                ? authors.stream()
                        .map(Author::toString)
                        .collect(Collectors.joining("\n"))
                : "авторы не найдены";
    }

    @ShellMethod(value = "creating author", key = {"author-create"})
    public String createAuthor(String name) {
        return "создан автор с id = " + authorService.create(new Author(name));
    }

    @ShellMethod(value = "updating author", key = {"author-update"})
    public String updateAuthor(Long id, String name) {
        return authorService.update(new Author(id, name)) ? "автор обновлен" : "автор не найден";
    }

    @ShellMethod(value = "deleting author", key = {"author-delete"})
    public String deleteAuthor(Long id) {
        return authorService.deleteById(id) ? "автор удален" : "автор не найден";
    }

    @ShellMethod(value = "getting genre by id", key = {"genre-get"})
    public String getGenreById(Long id) {
        Genre genre = genreService.getById(id);
        return genre != null ? genre.toString() : "жанр не найден";
    }

    @ShellMethod(value = "getting all genres", key = {"genre-get-all"})
    public String getAllGenres() {
        List<Genre> genres = genreService.getAll();
        return !genres.isEmpty()
                ? genres.stream()
                        .map(Genre::toString)
                        .collect(Collectors.joining("\n"))
                : "жанры не найдены";
    }

    @ShellMethod(value = "creating genre", key = {"genre-create"})
    public String createGenre(String name) {
        return "создан жанр с id = " + genreService.create(new Genre(name));
    }

    @ShellMethod(value = "updating genre", key = {"genre-update"})
    public String updateGenre(Long id, String name) {
        return genreService.update(new Genre(id, name)) ? "жанр обновлен" : "жанр не найден";
    }

    @ShellMethod(value = "deleting genre", key = {"genre-delete"})
    public String deleteGenre(Long id) {
        return genreService.deleteById(id) ? "жанр удален" : "жанр не найден";
    }
}
