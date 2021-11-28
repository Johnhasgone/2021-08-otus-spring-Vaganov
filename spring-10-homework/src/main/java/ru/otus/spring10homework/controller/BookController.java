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


//    @PostMapping("/book/{id}/comment")
//    public String bookAddComment(@PathVariable Long id, CommentDto commentNew) {
//        commentService.save(id, commentNew.getText());
//        return "redirect:/book/{id}";
//    }
//
//    @PostMapping("/book/create")
//    public String bookCreate(BookDto book) {
//        bookService.save(book);
//        return "redirect:/book";
//    }
//
//    @PostMapping("/book/edit")
//    public String bookEdit(BookDto book) {
//        BookDto saved = bookService.save(book);
//        return "redirect:/book";
//    }
//

}
