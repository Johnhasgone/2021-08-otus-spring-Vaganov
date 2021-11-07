package ru.otus.spring08homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring08homework.domain.Author;

import java.util.List;

@DisplayName("DAO для работы с авторами книг должно ")
@DataMongoTest
@EnableConfigurationProperties
class AuthorRepositoryTest {
    private static final Long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Афанасий Афанасьевич Фет";
    private static final String EXPECTED_AUTHOR_NAME = "Осип Мандельштам";

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("получать автора по имени")
    @Test
    void shouldGetAuthorByName() {
        List<Author> actualAuthor = authorRepository.findByName("author");
        actualAuthor.stream().map(Author::toString).forEach(System.out::println);


    }
}