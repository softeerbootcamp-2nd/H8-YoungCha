package com.youngcha.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    //응답에서 data 안의 내용을 객체로 변환
    protected <T> T extractDataFromResponse(ResultActions actions, TypeReference<T> typeReference) throws Exception {
        String jsonResponse = actions.andReturn().getResponse().getContentAsString();
        JsonNode responseNode = objectMapper.readTree(jsonResponse);
        JsonNode dataNode = responseNode.get("data");

        return objectMapper.readValue(dataNode.traverse(), typeReference);
    }
}
