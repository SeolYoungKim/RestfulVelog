package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.exception.NullArticleException;
import ToyProject.RestfulVelog.service.ArticleDto;
import ToyProject.RestfulVelog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Article readArticle(@PathVariable Long id) throws NullArticleException {

        if (articleService.findById(id) == null)
            throw new NullArticleException("글이 없습니다.");

        return articleService.findById(id);
    }

    @PostMapping("/article")
    public void postArticle(@RequestBody @Validated ArticleDto articleDto) {
        articleService.saveArticle(articleDto);
    }
}
