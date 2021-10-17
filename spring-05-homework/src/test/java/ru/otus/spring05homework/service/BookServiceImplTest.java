package ru.otus.spring05homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring05homework.dao.BookDao;
import ru.otus.spring05homework.domain.Author;
import ru.otus.spring05homework.domain.Book;
import ru.otus.spring05homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с книгами должен ")
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    BookService bookService;
    @MockBean
    BookDao bookDao;

    @DisplayName("возвращать ожидаемую книгу по ID")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(1L, "Стихотворения", new Genre(1L, "поэзия"), new Author(1L, "Афанасий Афанасьевич Фет"));
        when(bookDao.getById(any())).thenReturn(expectedBook);
        Book actualBook = bookService.getById(1L);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по названию")
    @Test
    void shouldReturnExpectedBookByName() {
        Book expectedBook = new Book(1L, "Стихотворения", new Genre(1L, "поэзия"), new Author(1L, "Афанасий Афанасьевич Фет"));
        when(bookDao.getByTitle(any())).thenReturn(expectedBook);
        Book actualBook = bookService.getByName("Стихотворения");
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать все книги")
    @Test
    void shouldReturnAllBooks() {
        List<Book> expected = List.of(
                new Book(1L, "Стихотворения", new Genre(1L, "поэзия"), new Author(1L, "Афанасий Афанасьевич Фет")),
                new Book(2L, "Рассказы", new Genre(2L, "проза"), new Author(2L, "Сергей Михалков"))
        );
        when(bookDao.getAll()).thenReturn(expected);
        List<Book> actual = bookService.getAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("возвращать ID созданной книги")
    @Test
    void shouldCreateBook() {
        Book creatingBook = new Book(1L, "Стихотворения", new Genre(1L, "поэзия"), new Author(1L, "Афанасий Афанасьевич Фет"));
        when(bookDao.insert(any())).thenReturn(creatingBook.getId());
        Long id = bookService.create(creatingBook);
        assertThat(id).isEqualTo(creatingBook.getId());
    }

    @DisplayName("вызывать bookDao.update()")
    @Test
    void shouldInvokeUpdateInDao() {
        Book updatingBook = new Book(1L, "Стихотворения", new Genre(1L, "поэзия"), new Author(1L, "Афанасий Афанасьевич Фет"));
        when(bookDao.update(updatingBook)).thenReturn(true);
        assertThat(bookService.update(updatingBook)).isTrue();
    }

    @DisplayName("вызывать bookDao.delete()")
    @Test
    void shouldInvokeDeleteInDao() {
        Long id = 1L;
        when(bookDao.deleteById(id)).thenReturn(true);
        assertThat(bookService.deleteById(id)).isTrue();
    }
}