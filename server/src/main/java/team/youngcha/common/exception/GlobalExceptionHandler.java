package team.youngcha.common.exception;

import team.youngcha.common.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // @RequestParam 오류
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("MissingServletRequestParameterException : {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("잘못된 파라미터입니다.", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // @RequestBody 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDtoField(MethodArgumentNotValidException e) {
        logger.error("MethodArgumentNotValidException : {}", e.getMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = createValidationErrorMessage(fieldErrors);
        ErrorResponse errorResponse = new ErrorResponse("잘못된 바디입니다.", errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // json 형식 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("HttpMessageNotReadableException : {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("잘못된 json 형식입니다.", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // @ModelAttribute 오류
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleModelAttributeValidationErrors(BindException e) {
        logger.error("BindException : {}", e.getMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = createValidationErrorMessage(fieldErrors);
        ErrorResponse errorResponse = new ErrorResponse("잘못된 파라미터입니다.", errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    //enum 타입 불일치로 바인딩 오류
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        logger.error("MethodArgumentTypeMismatchException : {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("데이터 타입이 일치하지 않습니다.", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(CustomException e) {
        logger.error("CustomException : {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getDetail());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception e, final HttpServletRequest request) {
        logger.error("Exception : {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("예상치 못한 오류가 발생했습니다.", null);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    private String createValidationErrorMessage(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }
}
