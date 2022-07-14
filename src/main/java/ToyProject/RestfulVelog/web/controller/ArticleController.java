package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.exception.NullArticleException;
import ToyProject.RestfulVelog.request.RequestArticleDto;
import ToyProject.RestfulVelog.response.ResponseArticleDto;
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
    public ResponseArticleDto readArticle(@PathVariable Long id) throws NullArticleException {
        return articleService.findById(id);
    }

    @GetMapping("/articles")
    public List<ResponseArticleDto> readAllArticles() {
        return articleService.findAll();
    }

    @PostMapping("/write")
    public ResponseArticleDto writeArticle(@RequestBody @Validated RequestArticleDto requestArticleDto) {
        return articleService.saveArticle(requestArticleDto);
    }

    @PostMapping("/article/{id}/edit")
    public ResponseArticleDto updateArticle(
            @PathVariable Long id,
            @RequestBody @Validated RequestArticleDto requestArticleDto) throws NullArticleException {

            return articleService.editArticle(id, requestArticleDto);

    }

}
