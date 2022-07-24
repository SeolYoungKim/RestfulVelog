package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.exception.ArticleNotFound;
import ToyProject.RestfulVelog.service.ArticleService;
import ToyProject.RestfulVelog.web.request.AddArticle;
import ToyProject.RestfulVelog.web.request.ArticleSearch;
import ToyProject.RestfulVelog.web.request.EditArticle;
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
    public ResponseArticleDto readArticle(@PathVariable Long id) throws ArticleNotFound {
        return articleService.findById(id);
    }

    //페이징 처리 - 호돌맨
    @GetMapping("/articles")
    public List<ResponseArticleDto> readAllArticles(@ModelAttribute ArticleSearch articleSearch) {
        return articleService.getList(articleSearch);
    }

    //Page 객체를 이용한 페이징 처리 TODO: sort를 LocalDateTime으로 해줘도 괜찮을듯.
    @GetMapping("/page")
    public Page<ResponseArticleDto> paging(
            @PageableDefault(size = 5, sort = "aId", direction = DESC) Pageable pageable) {
        return articleService.findAllToPage(pageable);

    }

    @PostMapping("/write")
    public ResponseArticleDto writeArticle(@RequestBody @Validated AddArticle addArticle) {
//        if (addArticle.getTitle().contains("바보")) {  // 데이터를 꺼내와서 조합하고 검증하는 것은 지양한다.
//            throw new InvalidRequest();
//        }

        addArticle.validate();

        return articleService.saveArticle(addArticle);
    }

    @PatchMapping ("/article/{id}")
    public ResponseArticleDto editArticle(
            @PathVariable Long id,
            @RequestBody @Validated EditArticle editArticle) throws ArticleNotFound {

        return articleService.editArticle(id, editArticle);
    }

    @DeleteMapping("article/{id}")
    public String deleteMapping(@PathVariable Long id) throws ArticleNotFound {
        log.info("delete-ok");
        return articleService.deleteArticle(id);
    }

}
