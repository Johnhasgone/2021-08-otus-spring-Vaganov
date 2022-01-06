package ru.otus.spring13homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.spring13homework.domain.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAuthorizedFindAll() {
        List<Book> books = bookRepository.findAll();
        assertNotNull(books);
        assertEquals(1, books.size());
    }

    @Test
    @WithMockUser(username = "user", roles = {"STUDENT", "TEACHER"})
    public void testUnauthorizedFindAll() {
        List<Book> books = bookRepository.findAll();
        assertNotNull(books);
        assertEquals(0, books.size());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAuthorizedFindById() {
        Book book = bookRepository.findBookById(1L);
        assertNotNull(book);
    }

    @Test
    @WithMockUser(username = "user", roles = {"STUDENT", "TEACHER"})
    public void testUnauthorizedFindById() {
        assertThrows(AccessDeniedException.class, () -> bookRepository.findBookById(1L));
    }
}