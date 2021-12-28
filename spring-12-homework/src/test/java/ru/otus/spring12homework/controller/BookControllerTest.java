package ru.otus.spring12homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring12homework.dto.BookDto;
import ru.otus.spring12homework.dto.CommentDto;
import ru.otus.spring12homework.service.BookService;
import ru.otus.spring12homework.service.CommentService;

import javax.sql.DataSource;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private CommentService commentService;

    @Test
    @WithMockUser(username = "admin")
    void loginPageTest() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    void indexPageTest() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    void bookListPageTest() throws Exception {
        when(bookService.findAll()).thenReturn(List.of());
        mvc.perform(get("/book"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    void bookPageTest() throws Exception {
        when(bookService.findById(any())).thenReturn(new BookDto());
        mvc.perform(get("/book/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    void bookAddCommentTest() throws Exception {
        when(commentService.save(any(), any())).thenReturn(new CommentDto());
        mvc.perform(post("/book/1/comment").with(csrf()))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser(username = "admin")
    void bookEditPageTest() throws Exception {
        when(bookService.findById(any())).thenReturn(new BookDto());
        mvc.perform(get("/book/1/edit"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    void bookCreatePageTest() throws Exception {
        mvc.perform(get("/book/create"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    void bookCreateTest() throws Exception {
        when(bookService.save(any())).thenReturn(new BookDto());
        mvc.perform(post("/book/create").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin")
    void bookEditTest() throws Exception {
        when(bookService.save(any())).thenReturn(new BookDto());
        mvc.perform(post("/book/edit").with(csrf()))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser(username = "admin")
    void bookDeleteTest() throws Exception {
        doNothing().when(bookService).deleteById(any());
        mvc.perform(delete("/book/1").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}