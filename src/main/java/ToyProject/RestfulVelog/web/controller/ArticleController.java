package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.exception.NullArticleException;
import ToyProject.RestfulVelog.service.ArticleDto;
import ToyProject.RestfulVelog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return articleService.findById(id);
    }

    @GetMapping("/articles")
    public List<Article> readAllArticles() {
        return articleService.findAll();
    }

    @PostMapping("/write")
    public void writeArticle(@RequestBody @Validated ArticleDto articleDto) {
        articleService.saveArticle(articleDto);
    }

    @PostMapping("/article/{id}/edit")
    public void updateArticle(
            @PathVariable Long id,
            @RequestBody @Validated ArticleDto articleDto) throws NullArticleException {

            articleService.editArticle(id, articleDto);

    }

}
