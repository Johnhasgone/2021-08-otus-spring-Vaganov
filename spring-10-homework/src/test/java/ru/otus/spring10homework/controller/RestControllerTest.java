package ru.otus.spring10homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring10homework.dto.BookDto;
import ru.otus.spring10homework.dto.CommentDto;
import ru.otus.spring10homework.service.BookService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class RestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getAllTest() throws Exception {
        List<BookDto> expected = List.of(
                new BookDto(1L, "Стихотворения", "Афанасий Афанасьевич Фет", "поэзия"),
                new BookDto(2L, "Сборник рассказов", "Сергей Михалков", "проза"),
                new BookDto(3L, "Улитка на склоне", "Аркадий Стругацкий, Борис Стругацкий", "проза, роман")
        );

        mvc.perform(get("/rest/book"))
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void getBookTest() throws Exception {
        BookDto expected = new BookDto(1L, "Стихотворения", "Афанасий Афанасьевич Фет", "поэзия");

        mvc.perform(get("/rest/book/1"))
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void deleteBookTest() throws Exception {
        mvc.perform(delete("/rest/book/1"))
                .andExpect(status().isOk());
        assertThrows(EmptyResultDataAccessException.class, () -> bookService.findById(1L));
    }

    @Test
    void saveBookTest() throws Exception {
        BookDto expectedDto = new BookDto(4L, "TestTitle", "TestAuthor", "TestGenre");
        String expected = objectMapper.writeValueAsString(expectedDto);
        mvc.perform(post("/rest/book")
                .content(expected)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().json(expected));

        assertEquals(expectedDto, bookService.findById(4L));
    }

    @Test
    void addCommentTest() throws Exception {
        String expected = objectMapper.writeValueAsString(new CommentDto(8L, "test comment"));

        mvc.perform(post("/rest/book/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected)
        ).andExpect(content().json(expected));
    }

    @Test
    void getCommentsTest() throws Exception {
        List<CommentDto> expected = List.of(
                new CommentDto(1L, "Отличная книга"),
                new CommentDto(2L, "Превосходно"),
                new CommentDto(3L, "Так себе")
        );

        mvc.perform(get("/rest/book/1/comment"))
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }
}