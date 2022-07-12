package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.exception.ArticleException;
import ToyProject.RestfulVelog.service.ArticleDto;
import ToyProject.RestfulVelog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, String> postArticle(@RequestBody @Validated ArticleDto articleDto) {

        articleService.saveArticle(articleDto);

        return Map.of();  //TODO : 다른 좋은 반환값이 있다면 생각해보자. 리다이렉션과 연관지어볼 수 있을듯.
    }
}
