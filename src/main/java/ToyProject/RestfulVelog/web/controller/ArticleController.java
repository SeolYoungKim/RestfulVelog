package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.exception.NullArticleException;
import ToyProject.RestfulVelog.service.ArticleService;
import ToyProject.RestfulVelog.web.request.RequestArticleDto;
import ToyProject.RestfulVelog.web.response.ResponseArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

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

    //페이징 처리 - 호돌맨
    @GetMapping("/articles")
    public List<ResponseArticleDto> readAllArticles(Pageable pageable) {
        return articleService.findAll(pageable);
    }

    //Page 객체를 이용한 페이징 처리 TODO: sort를 LocalDateTime으로 해줘도 괜찮을듯.
    @GetMapping("/page")
    public Page<ResponseArticleDto> paging(
            @PageableDefault(size = 5, sort = "aId", direction = DESC) Pageable pageable) {
        return articleService.findAllToPage(pageable);

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
