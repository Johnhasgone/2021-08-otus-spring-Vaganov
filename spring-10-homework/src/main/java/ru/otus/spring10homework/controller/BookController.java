package ru.otus.spring10homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring10homework.dto.BookDto;
import ru.otus.spring10homework.dto.CommentDto;
import ru.otus.spring10homework.service.BookService;
import ru.otus.spring10homework.service.CommentService;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/rest/book")
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/rest/book/{id}")
    public BookDto bookPage(@PathVariable Long id) {
        return bookService.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + id, 1));
    }

    @DeleteMapping("/rest/book/{id}")
    public void bookDelete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PostMapping("/rest/book/save")
    public BookDto bookSave(@RequestBody BookDto book) {
        return bookService.save(book);
    }

    @PostMapping("/rest/book/{id}/comment")
    public CommentDto bookAddComment(@PathVariable Long id, @RequestBody CommentDto commentNew) {
        return commentService.save(id, commentNew.getText());
    }

    @GetMapping("/rest/book/{id}/comment")
    public List<CommentDto> getComments(@PathVariable Long id) {
        return commentService.findByBookId(id);
    }
}
