package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.exception.VelogException;
import ToyProject.RestfulVelog.web.response.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult articleHandler(MethodArgumentNotValidException e) {
        log.error("[ERROR]", e);

        List<FieldError> fieldErrors = e.getFieldErrors();

        ErrorResult errorResult = ErrorResult.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : fieldErrors) {
            errorResult.causedByField(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorResult;
    }

    //TODO: 예외처리 하는 방식 한번 더 복습!
    @ExceptionHandler(VelogException.class)
    public ResponseEntity<ErrorResult> velogException(VelogException e) {
        log.error("[ERROR]", e);

        int statusCode = e.getStatusCode();
        String message = e.getMessage();
        Map<String, String> validation = e.getValidation();

        ErrorResult errorResult = ErrorResult.builder()
                .code(String.valueOf(statusCode))
                .message(message)
                .causedBy(validation)
                .build();

        return ResponseEntity.status(statusCode)
                .body(errorResult);
    }


}





