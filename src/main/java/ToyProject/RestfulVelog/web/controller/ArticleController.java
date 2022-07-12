package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import ToyProject.RestfulVelog.service.ArticleDto;
import ToyProject.RestfulVelog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String home() {
        return "Hi!";
    }

    @GetMapping("/article/{id}")
    public Article readArticle(@PathVariable Long id) {
        log.info("왜 안나오냐? id={}", id);
        log.info("왜 안나오냐? Article={}", articleService.findById(id));
        return articleService.findById(id);
    }

    @PostMapping("/article")
    public Map<String, String> postArticle(@RequestBody @Validated ArticleDto articleDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();

            return Map.of(field, message);
        }

        articleService.saveArticle(articleDto);

        return Map.of();  //TODO : 다른 좋은 반환값이 있다면 생각해보자. 리다이렉션과 연관지어볼 수 있을듯.
    }
}
