package com.mrocabado.s4.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenIdIsFoundShouldReturnStudent() throws Exception {
        //https://spring.io/guides/gs/testing-web/
        mockMvc.perform(get("/students/{filter}", "id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..id").value("1"))
                .andExpect(jsonPath("$..firstName").exists());
    }
}
