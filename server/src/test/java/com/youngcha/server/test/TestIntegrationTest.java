package com.youngcha.server.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestIntegrationTest {

    @Autowired
    TestController testController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private <T> T extractDataFromResponse(ResultActions actions, TypeReference<T> typeReference) throws Exception {
        String jsonResponse = actions.andReturn().getResponse().getContentAsString();
        JsonNode responseNode = objectMapper.readTree(jsonResponse);
        JsonNode dataNode = responseNode.get("data");

        return objectMapper.readValue(dataNode.traverse(), typeReference);
    }

    @Test
    @DisplayName("성공 응답을 받는다.")
    void test() throws Exception {
        //given
        String url = "/test/ok";

        //when
        ResultActions actions = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        String response = extractDataFromResponse(actions, new TypeReference<>() {
        });
        assertThat(response).isEqualTo("ok");
    }

    @Test
    @DisplayName("bad request 예외가 발생한다.")
    void error() throws Exception {
        //given
        String url = "/test/error";

        //when
        ResultActions actions = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then
        actions.andExpect(jsonPath("$.message").value("error"));
    }
}