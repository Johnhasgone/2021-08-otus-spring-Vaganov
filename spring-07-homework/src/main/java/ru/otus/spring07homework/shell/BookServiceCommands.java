package ru.otus.spring07homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring07homework.dto.AuthorDto;
import ru.otus.spring07homework.dto.BookDto;
import ru.otus.spring07homework.dto.CommentDto;
import ru.otus.spring07homework.dto.GenreDto;
import ru.otus.spring07homework.service.AuthorService;
import ru.otus.spring07homework.service.BookService;
import ru.otus.spring07homework.service.CommentService;
import ru.otus.spring07homework.service.GenreService;

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
    private final CommentService commentService;


    @ShellMethod(value = "creating book", key = {"book-create"})
    public String createBook(String title, String authorNames, String genreNames) {

        List<String> genreNameArray = Arrays.stream(genreNames.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        List<String> authorNameArray = Arrays.stream(authorNames.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        return  "создана книга с id = " + bookService.save(title, authorNameArray, genreNameArray).getId();
    }

    @ShellMethod(value = "getting book by id", key = {"book-get"})
    public String getBookById(Long id) {
        Optional<BookDto> book = bookService.findById(id);
        return book.isPresent() ? book.get().toString() : "книга не найдена";
    }

    @ShellMethod(value = "getting all books", key = {"book-get-all"})
    public String getAllBooks() {
        List<BookDto> books = bookService.findAll();

        return !books.isEmpty()
                ? books.stream()
                        .map(BookDto::toString)
                        .collect(Collectors.joining("\n"))
                : "книги не найдены";
    }

    @ShellMethod(value = "updating book", key = {"book-update"})
    public String updateBook(Long id, String title) {
        if (!bookService.existsById(id)) {
            return "книга не найдена";
        }
        bookService.updateNameById(id, title);
        return "книга обновлена";
    }

    @ShellMethod(value = "deleting book", key = {"book-delete"})
    public String deleteBook(Long id) {
        if (!bookService.existsById(id)) {
            return "книга не найдена";
        }
        bookService.deleteById(id);

        return "книга удалена";
    }

    @ShellMethod(value = "adding comment to book", key = {"book-add-comment"})
    public String addCommentToBook(Long id, String text) {
        Optional<BookDto> book = bookService.findById(id);

        if (book.isEmpty()) {
            return "Книга с id = " + id + "не найдена";
        }

        CommentDto commentDto = commentService.save(text, book.get());
        return commentDto.getId() != null ? "Комментарий сохранен" : "Не удалось сохранить комментарий";
    }

    @ShellMethod(value = "getting all comments for book", key = {"book-get-comments"})
    public String getCommentsByBookId(Long id) {

        if (!bookService.existsById(id)) {
            return "Книга с id = " + id + " не найдена";
        }

        return commentService.findByBookId(id)
                .stream()
                .map(CommentDto::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "get comment by id", key = {"comment-get"})
    public String getCommentById(Long id) {
        Optional<CommentDto> comment =  commentService.findById(id);
        return comment.isPresent() ? comment.get().toString() : "Комментарий не найден";
    }

    @ShellMethod(value = "deleting comment by id", key = {"comment-delete"})
    public String deleteCommentById(Long id) {
        if (!commentService.existsById(id)) {
            return "Комментарий не найден";
        }
        commentService.deleteById(id);
        return "Комментарий удален";
    }

    @ShellMethod(value = "update comment by id", key = {"comment-update"})
    public String updateCommentById(Long id, String text) {
        if (!commentService.existsById(id)) {
            return "Комментарий не найден";
        }
        commentService.updateTextById(id, text);
        return "Комментарий обновлен";
    }

    @ShellMethod(value = "getting author by id", key = {"author-get"})
    public String getAuthorById(Long id) {
        Optional<AuthorDto> author = authorService.findById(id);
        return author.isPresent() ? author.get().toString() : "автор не найден";
    }

    @ShellMethod(value = "getting all authors", key = {"author-get-all"})
    public String getAllAuthors() {
        List<AuthorDto> authors = authorService.findAll();
        return !authors.isEmpty()
                ? authors.stream()
                        .map(AuthorDto::toString)
                        .collect(Collectors.joining("\n"))
                : "авторы не найдены";
    }

    @ShellMethod(value = "creating author", key = {"author-create"})
    public String createAuthor(String name) {
        return "создан автор с id = " + authorService.save(name).getId();
    }

    @ShellMethod(value = "updating author", key = {"author-update"})
    public String updateAuthor(Long id, String name) {
        if (!authorService.existsById(id)) {
            return "автор не найден";
        }
        authorService.updateNameById(id, name);
        return "автор обновлен";
    }

    @ShellMethod(value = "deleting author", key = {"author-delete"})
    public String deleteAuthor(Long id) {
        if (!authorService.existsById(id)) {
            return "автор не найден";
        }
        authorService.deleteById(id);
        return "автор удален";
    }

    @ShellMethod(value = "getting genre by id", key = {"genre-get"})
    public String getGenreById(Long id) {
        Optional<GenreDto> genre = genreService.findById(id);
        return genre.isPresent() ? genre.get().toString() : "жанр не найден";
    }

    @ShellMethod(value = "getting all genres", key = {"genre-get-all"})
    public String getAllGenres() {
        List<GenreDto> genres = genreService.findAll();
        return !genres.isEmpty()
                ? genres.stream()
                        .map(GenreDto::toString)
                        .collect(Collectors.joining("\n"))
                : "жанры не найдены";
    }

    @ShellMethod(value = "creating genre", key = {"genre-create"})
    public String createGenre(String name) {
        return "создан жанр с id = " + genreService.save(name).getId();
    }

    @ShellMethod(value = "updating genre", key = {"genre-update"})
    public String updateGenre(Long id, String name) {
        if (!genreService.existsById(id)) {
            return "жанр не найден";
        }
        genreService.updateNameById(id, name);
        return "жанр обновлен";
    }

    @ShellMethod(value = "deleting genre", key = {"genre-delete"})
    public String deleteGenre(Long id) {
        if (!genreService.existsById(id)) {
            return "жанр не найден";
        }
        genreService.deleteById(id);
        return "жанр удален";
    }
}