package ru.otus.spring09homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring09homework.dto.BookDto;
import ru.otus.spring09homework.dto.CommentDto;
import ru.otus.spring09homework.service.BookService;
import ru.otus.spring09homework.service.CommentService;

import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/book")
    public String bookListPage(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/book/{id}")
    public String bookPage(Model model, @PathVariable Long id) {
        BookDto book = bookService.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + id, 1));
        List<CommentDto> comments = commentService.findByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        model.addAttribute("commentNew", new CommentDto());
        return "bookOpen";
    }

    @PostMapping("/book/{id}/comment")
    public String bookAddComment(@PathVariable Long id, CommentDto commentNew) {
        commentService.save(id, commentNew.getText());
        return "redirect:/book/{id}";
    }

    @GetMapping("/book/{id}/edit")
    public String bookEditPage(@PathVariable Long id, Model model) {
        BookDto book = bookService.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + id, 1));
        model.addAttribute("book", book);
        return "bookEdit";
    }

    @GetMapping("/book/create")
    public String bookCreatePage(Model model) {
        model.addAttribute("book", new BookDto());
        return "bookEdit";
    }

    @PostMapping("/book/create")
    public String bookCreate(BookDto book) {
        bookService.save(book);
        return "redirect:/book";
    }

    @PostMapping("/book/edit")
    public String bookEdit(BookDto book) {
        BookDto saved = bookService.save(book);
        return "redirect:/book";
    }

    @DeleteMapping("/book/{id}")
    public String bookDelete(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/book";
    }
}
