package ru.otus.spring09homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void bookListPageTest() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk());
    }

    @Test
    void bookPageTest() throws Exception {
        mvc.perform(get("/book/1"))
                .andExpect(status().isOk());
    }

    @Test
    void bookAddCommentTest() throws Exception {
        mvc.perform(post("/book/1/comment"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    void bookEditPageTest() throws Exception {
        mvc.perform(get("/book/1/edit"))
                .andExpect(status().isOk());
    }

    @Test
    void bookCreatePageTest() throws Exception {
        mvc.perform(get("/book/create"))
                .andExpect(status().isOk());
    }

    @Test
    void bookCreateTest() throws Exception {
        mvc.perform(post("/book/create"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void bookEditTest() throws Exception {
        mvc.perform(post("/book/edit"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    void bookDeleteTest() throws Exception {
        mvc.perform(delete("/book/1"))
                .andExpect(status().is3xxRedirection());
    }
}