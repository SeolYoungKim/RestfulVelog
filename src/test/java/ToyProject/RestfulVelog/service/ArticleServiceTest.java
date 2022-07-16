package ToyProject.RestfulVelog.service;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import ToyProject.RestfulVelog.web.request.ArticleSearch;
import ToyProject.RestfulVelog.web.request.RequestArticleDto;
import ToyProject.RestfulVelog.web.response.ResponseArticleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @BeforeEach
    void clear() {
        articleRepository.deleteAll();
    }

    @Test
    @Transactional
    void articleRepoTest() {
        //given
        Article article1 = Article.builder()
                .title("제목1")
                .text("제목2")
                .build();

        articleRepository.save(article1);

        //when
        List<Article> findByTitle = articleRepository.findByTitle("제목1");

        //then
        assertThat(findByTitle.get(0)).isEqualTo(article1);
        assertThat(findByTitle.size()).isEqualTo(1);
    }

    @DisplayName("글 1페이지 조회")
    @Transactional
    @Test
    void readAllArticle() {
        List<Article> requestArticles = IntStream.range(1, 31)
                .mapToObj(i -> Article.builder()
                                .title("제목 " + i)
                                .text("내용 " + i)
                                .build())
                .collect(Collectors.toList());

        articleRepository.saveAll(requestArticles);

        ArticleSearch articleSearch = ArticleSearch.builder()
                .build();


        List<ResponseArticleDto> articles = articleService.getList(articleSearch);

        assertThat(articles.size()).isEqualTo(10);
        assertThat(articles.get(0).getTitle()).isEqualTo("제목 30");
        assertThat(articles.get(9).getTitle()).isEqualTo("제목 21");
    }

    @DisplayName("글 수정 테스트")
    @Transactional
    @Test
    void articleEdit() throws Exception {
        Article article = Article.builder()
                .title("제목")
                .text("글")
                .build();

        RequestArticleDto requestArticleDto = new RequestArticleDto();
        requestArticleDto.setTitle("제목이지롱");
        requestArticleDto.setText("글이지롱");

        articleRepository.save(article);

        articleService.editArticle(article.getAId(), requestArticleDto);

        assertThat(article.getTitle()).isEqualTo("제목이지롱");
        assertThat(article.getText()).isEqualTo("글이지롱");

    }

}