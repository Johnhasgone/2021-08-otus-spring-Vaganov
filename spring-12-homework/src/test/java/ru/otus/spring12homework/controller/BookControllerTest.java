package ru.otus.spring12homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @ParameterizedTest
    @ValueSource(strings = {"/", "/book", "/book/1", "/book/1/edit", "/book/create"})
    @WithMockUser(username = "admin")
    void testGetUrlWithAuthenticationShouldReturnOk(String url) throws Exception {
        when(bookService.findById(any())).thenReturn(new BookDto());
        mvc.perform(get(url).with(csrf()))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/book", "/book/1", "/book/1/edit", "/book/create"})
    void testGetUrlWithoutAuthenticationShouldRedirectToLogin(String url) throws Exception {
        mvc.perform(get(url).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @ParameterizedTest
    @CsvSource({"/book/1/comment,/book/1", "/book/create,/", "/book/edit,/book"})
    @WithMockUser(username = "admin")
    void testPostUrlWithAuthenticationShouldRedirectToRightUrl(String url, String redirectUrl) throws Exception {
        when(commentService.save(any(), any())).thenReturn(new CommentDto());
        when(bookService.save(any())).thenReturn(new BookDto());
        mvc.perform(post(url).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/book/1/comment", "/book/create", "/book/edit"})
    void testPostUrlWithoutAuthenticationShouldBeForbidden(String url) throws Exception {
        mvc.perform(post(url).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    @WithMockUser(username = "admin")
    void testDeleteBookWithAuthenticationShouldRedirectToBookPage() throws Exception {
        doNothing().when(bookService).deleteById(any());
        mvc.perform(delete("/book/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    @Test
    void testDeleteBookWithoutAuthenticationShouldRedirectToLoginPage() throws Exception {
        mvc.perform(delete("/book/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }
}