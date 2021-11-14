package ru.otus.spring09homework.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring09homework.domain.Book;
import ru.otus.spring09homework.dto.AuthorDto;
import ru.otus.spring09homework.dto.BookDto;
import ru.otus.spring09homework.dto.GenreDto;
import ru.otus.spring09homework.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/book/edit")
    public String bookEditPage(@RequestParam Long id, Model model) {
        BookDto book = bookService.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Не найдена книга с id " + id, 1));
        model.addAttribute("book", book);
        return "bookEdit";
    }

    @PostMapping("/book/edit")
    public String bookEdit(BookDto book, Model model) {
//        BookDto saved = bookService.save(
//                book.getTitle(),
//                null,
//                null
//        );
        System.out.println("book id" + " " + book.getId() + " " + book.getTitle());
        bookService.updateNameById(book.getId(), book.getTitle());
        // TODO fix service method - should save entity by getting entity as parameter
        model.addAttribute("book", bookService.findById(book.getId()).get());
        // TODO add redirection
        return "bookEdit";
    }
}
