package ru.otus.spring16homework.controller;

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
import ru.otus.spring16homework.dto.BookDto;
import ru.otus.spring16homework.dto.CommentDto;
import ru.otus.spring16homework.service.BookService;
import ru.otus.spring16homework.service.CommentService;

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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetUrlWithAuthenticationShouldReturnOk(String url) throws Exception {
        when(bookService.findById(any())).thenReturn(new BookDto());
        mvc.perform(get(url).with(csrf()))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/book", "/book/1"})
    @WithMockUser(username = "user", roles = {"STUDENT", "TEACHER"})
    void testAuthorizedUrlWithAuthenticationShouldReturnOk(String url) throws Exception {
        when(bookService.findById(any())).thenReturn(new BookDto());
        mvc.perform(get(url).with(csrf()))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/book/1/edit", "/book/create"})
    @WithMockUser(username = "user", roles = {"STUDENT", "TEACHER"})
    void testUnauthorizedUrlWithAuthenticationShouldReturnForbidden(String url) throws Exception {
        when(bookService.findById(any())).thenReturn(new BookDto());
        mvc.perform(get(url).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @CsvSource({"/book/create,/", "/book/edit,/book"})
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAuthorizedPostUrlWithAuthenticationShouldRedirectToRightUrl(String url, String redirectUrl) throws Exception {
        when(bookService.save(any())).thenReturn(new BookDto());
        mvc.perform(post(url).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));
    }

    @ParameterizedTest
    @CsvSource({"/book/create,/", "/book/edit,/book"})
    @WithMockUser(username = "user", roles = {"STUDENT", "TEACHER"})
    void testUnauthorizedPostUrlWithAuthenticationShouldReturnForbidden(String url, String redirectUrl) throws Exception {
        when(bookService.save(any())).thenReturn(new BookDto());
        mvc.perform(post(url).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {"STUDENT", "TEACHER"})
    void testAuthorizedCreateCommentWithAuthenticationShouldRedirectToRightUrl() throws Exception {
        when(commentService.save(any(), any())).thenReturn(new CommentDto());
        mvc.perform(post("/book/1/comment").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book/1"))
        ;
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUnauthorizedCreateCommentWithAuthenticationShouldReturnForbidden() throws Exception {
        when(commentService.save(any(), any())).thenReturn(new CommentDto());
        mvc.perform(post("/book/1/comment").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAuthorizedDeleteBookWithAuthenticationShouldRedirectToBookPage() throws Exception {
        doNothing().when(bookService).deleteById(any());
        mvc.perform(delete("/book/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"STUDENT", "TEACHER"})
    void testUnauthorizedDeleteBookWithAuthenticationShouldRedirectToBookPage() throws Exception {
        doNothing().when(bookService).deleteById(any());
        mvc.perform(delete("/book/1").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/book", "/book/1", "/book/1/edit", "/book/create"})
    void testGetUrlWithoutAuthenticationShouldRedirectToLogin(String url) throws Exception {
        mvc.perform(get(url).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/book/1/comment", "/book/create", "/book/edit"})
    void testPostUrlWithoutAuthenticationShouldBeForbidden(String url) throws Exception {
        mvc.perform(post(url).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }


    @Test
    void testDeleteBookWithoutAuthenticationShouldRedirectToLoginPage() throws Exception {
        mvc.perform(delete("/book/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }
}