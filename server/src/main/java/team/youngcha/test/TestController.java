package team.youngcha.test;

import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/ok")
    public ResponseEntity<SuccessResponse> test() {
        SuccessResponse<String> response = new SuccessResponse<>("ok");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/error")
    public ResponseEntity<SuccessResponse> error() {
        throw new CustomException(HttpStatus.BAD_REQUEST, "error");
    }
}
