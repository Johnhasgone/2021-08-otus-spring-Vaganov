package ru.otus.spring12homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring12homework.dto.BookDto;
import ru.otus.spring12homework.dto.CommentDto;
import ru.otus.spring12homework.service.BookService;
import ru.otus.spring12homework.service.CommentService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String startPage(Model model) {
        return "index";
    }

    @GetMapping("/book")
    public String bookListPage(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/book/{id}")
    public String bookPage(Model model, @PathVariable Long id) {
        BookDto book = bookService.findById(id);
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
        BookDto book = bookService.findById(id);
        model.addAttribute("book", book);
        return "bookEdit";
    }

    @GetMapping("/book/create")
    public String bookCreatePage(Model model) {
        model.addAttribute("book", new BookDto());
        return "bookCreate";
    }

    @PostMapping("/book/create")
    public String bookCreate(BookDto book) {
        bookService.save(book);
        return "redirect:/";
    }

    @PostMapping("/book/edit")
    public String bookEdit(BookDto book) {
        bookService.save(book);
        return "redirect:/book";
    }

    @DeleteMapping("/book/{id}")
    public String bookDelete(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/book";
    }
}
