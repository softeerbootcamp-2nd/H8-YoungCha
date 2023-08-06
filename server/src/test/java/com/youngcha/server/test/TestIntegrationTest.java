package com.youngcha.server.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.youngcha.server.IntegrationTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TestIntegrationTest extends IntegrationTestBase {

    @Autowired
    TestController testController;

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