package team.youngcha.domain.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Tag(name = "Profile API", description = "Profile 변수 API")
@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final Environment env;

    // TODO : actuator
    @Operation(summary = "컨테이너 profile 조회", description = "구동 중인 컨테이너의 profile을 얻습니다.")
    @GetMapping
    String profile() {
        String[] activeProfiles = env.getActiveProfiles();
        List<String> springProfiles = List.of("spring1", "spring2");
        return Arrays.stream(activeProfiles).filter(springProfiles::contains).findAny()
                .orElse("spring1");
    }
}