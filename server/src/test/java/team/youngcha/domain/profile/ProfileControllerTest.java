package team.youngcha.domain.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProfileController.class)
class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Nested
    @TestPropertySource(properties = "spring.profiles.active=spring1")
    class Spring1Profile {
        @Test
        @DisplayName("spring1 속성을 얻는다.")
        void spring1() throws Exception {
            validateProfile();
        }
    }

    @Nested
    @TestPropertySource(properties = "spring.profiles.active=local")
    class EmptyProfile {
        @Test
        @DisplayName("spring1, spring2 중 어느 것도 없으면 spring1 속성을 얻는다.")
        void activeEmpty() throws Exception {
            validateProfile();
        }
    }

    private void validateProfile() throws Exception {
        //given
        String url = "/profiles";

        //when
        ResultActions resultActions = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("spring1"))
                .andDo(print());
    }
}