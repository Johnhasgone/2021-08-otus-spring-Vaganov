package ru.otus.spring10homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring10homework.dto.BookDto;
import ru.otus.spring10homework.dto.CommentDto;
import ru.otus.spring10homework.service.BookService;
import ru.otus.spring10homework.service.CommentService;

import java.util.List;

@Controller
public class PageController {
    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public PageController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/book")
    public String bookListPage() {
        return "bookList";
    }


//
//    @GetMapping("/book/{id}/edit")
//    public String bookEditPage(@PathVariable Long id, Model model) {
//        BookDto book = bookService.findById(id)
//                .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + id, 1));
//        model.addAttribute("book", book);
//        return "bookEdit";
//    }
//
//    @GetMapping("/book/create")
//    public String bookCreatePage(Model model) {
//        model.addAttribute("book", new BookDto());
//        return "bookEdit";
//    }
}
