package ru.otus.spring06homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring06homework.dao.BookDao;
import ru.otus.spring06homework.domain.Author;
import ru.otus.spring06homework.domain.Book;
import ru.otus.spring06homework.domain.Genre;

import java.util.List;
import java.util.Optional;

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
        Book expectedBook = new Book(1L, "Стихотворения", List.of(new Author(1L, "Афанасий Афанасьевич Фет")), List.of(new Genre(1L, "поэзия")));
        when(bookDao.findById(any())).thenReturn(Optional.of(expectedBook));
        Book actualBook = bookService.findById(1L).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по названию")
    @Test
    void shouldReturnExpectedBookByName() {
        List<Book> expectedBooks = List.of(new Book(1L, "Стихотворения", List.of(new Author(1L, "Афанасий Афанасьевич Фет")), List.of(new Genre(1L, "поэзия"))));
        when(bookDao.findByTitle(any())).thenReturn(expectedBooks);
        List<Book> actualBooks = bookService.findByTitle("Стихотворения");
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DisplayName("возвращать все книги")
    @Test
    void shouldReturnAllBooks() {
        List<Book> expected = List.of(
                new Book(1L, "Стихотворения", List.of(new Author(1L, "Афанасий Афанасьевич Фет")), List.of(new Genre(1L, "поэзия"))),
                new Book(2L, "Рассказы", List.of(new Author(2L, "Сергей Михалков")), List.of(new Genre(2L, "проза")))
        );
        when(bookDao.findAll()).thenReturn(expected);
        List<Book> actual = bookService.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("возвращать ID созданной книги")
    @Test
    void shouldCreateBook() {
        Book creatingBook = new Book(1L, "Стихотворения", List.of(new Author(1L, "Афанасий Афанасьевич Фет")), List.of(new Genre(1L, "поэзия")));
        when(bookDao.save(any())).thenReturn(creatingBook);
        Book actualBook = bookService.save(creatingBook);
        assertThat(actualBook).isEqualTo(creatingBook);
    }

    @DisplayName("вызывать bookDao.update()")
    @Test
    void shouldInvokeUpdateInDao() {
        Book updatingBook = new Book(1L, "Стихотворения", List.of(new Author(1L, "Афанасий Афанасьевич Фет")), List.of(new Genre(1L, "поэзия")));
        when(bookDao.update(updatingBook)).thenReturn(1);
        assertThat(bookService.update(updatingBook)).isTrue();
    }

    @DisplayName("вызывать bookDao.delete()")
    @Test
    void shouldInvokeDeleteInDao() {
        Long id = 1L;
        when(bookDao.deleteById(id)).thenReturn(1);
        assertThat(bookService.deleteById(id)).isTrue();
    }
}