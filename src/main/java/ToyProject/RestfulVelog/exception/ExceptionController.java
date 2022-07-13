package ToyProject.RestfulVelog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult articleHandler(MethodArgumentNotValidException e) {
        log.error("[ERROR]", e);

        List<FieldError> fieldErrors = e.getFieldErrors();

        ErrorResult errorResult = ErrorResult.builder()
                .code("404")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : fieldErrors) {
            errorResult.causedByField(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorResult;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullArticleException.class)
    public ErrorResult nullArticleHandler(NullArticleException e) {
        log.error("[ERROR]", e);

        String message = e.getMessage();

        ErrorResult errorResult = ErrorResult.builder()
                .code("404")
                .message("잘못된 요청입니다.")
                .build();

        errorResult.causedByField("pathVariable", "조회할 글이 없습니다.");

        return errorResult;
    }
}





