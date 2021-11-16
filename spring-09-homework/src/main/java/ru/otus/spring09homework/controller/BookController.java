package ru.otus.spring09homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring09homework.dto.BookDto;
import ru.otus.spring09homework.service.BookService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public String bookListPage(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "bookList";
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
