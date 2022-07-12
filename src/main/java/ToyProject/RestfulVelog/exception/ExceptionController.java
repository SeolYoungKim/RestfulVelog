package ToyProject.RestfulVelog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> articleHandler(ArticleException e) {
        log.error("[글 관련 에러 발생]", e);
        ErrorResult errorResult = new ErrorResult("ARTICLE-EX", e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
