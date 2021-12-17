//package ru.otus.spring11homework.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.otus.spring11homework.dto.BookDto;
//import ru.otus.spring11homework.dto.CommentDto;
//import ru.otus.spring11homework.service.BookService;
//import ru.otus.spring11homework.service.CommentService;
//
//import java.util.List;
//
//@org.springframework.web.bind.annotation.RestController
//public class RestController {
//
//    private final BookService bookService;
//    private final CommentService commentService;
//
//    @Autowired
//    public RestController(BookService bookService, CommentService commentService) {
//        this.bookService = bookService;
//        this.commentService = commentService;
//    }
//
//    @GetMapping("/rest/book")
//    public List<BookDto> getAll() {
//        return bookService.findAll();
//    }
//
//    @GetMapping("/rest/book/{id}")
//    public BookDto getBook(@PathVariable Long id) {
//        return bookService.findById(id);
//    }
//
//    @DeleteMapping("/rest/book/{id}")
//    public void deleteBook(@PathVariable Long id) {
//        bookService.deleteById(id);
//    }
//
//    @PostMapping("/rest/book")
//    public BookDto saveBook(@RequestBody BookDto book) {
//        return bookService.save(book);
//    }
//
//    @PostMapping("/rest/book/{id}/comment")
//    public CommentDto addComment(@PathVariable Long id, @RequestBody CommentDto commentNew) {
//        return commentService.save(id, commentNew.getText());
//    }
//
//    @GetMapping("/rest/book/{id}/comment")
//    public List<CommentDto> getComments(@PathVariable Long id) {
//        return commentService.findByBookId(id);
//    }
//}
