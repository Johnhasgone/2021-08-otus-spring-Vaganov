package ru.otus.spring08homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring08homework.domain.Book;

import java.util.List;

@DisplayName("DAO для работы с книгами должно ")
@DataMongoTest
@EnableConfigurationProperties
//@ComponentScan({"ru.otus.example.mongodbdemo.config", "ru.otus.example.mongodbdemo.repositories"})
class BookRepositoryTest {
    private static final String EXPECTED_BOOK_TITLE = "Lyrics";
    private static final String BOOK_ID = "618798e353e7dc368d2a49f9";


    @Autowired
    private BookRepository bookRepository;

    @DisplayName("получать книгу по имени")
    @Test
    void shouldGetBookByName() {
        List<Book> actualBooks = bookRepository.findByTitle("alug");
        actualBooks.stream().map(Book::getTitle).forEach(System.out::println);
    }
}