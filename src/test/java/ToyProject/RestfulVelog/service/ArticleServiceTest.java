package ToyProject.RestfulVelog.service;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.repository.ArticleRepository;
import ToyProject.RestfulVelog.exception.NullArticleException;
import ToyProject.RestfulVelog.web.request.AddArticle;
import ToyProject.RestfulVelog.web.request.ArticleSearch;
import ToyProject.RestfulVelog.web.request.EditArticle;
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

    @DisplayName("글 리포지토리에 직접 저장 테스트")
    @Test
    @Transactional
    void articleRepoTest() {
        //given
        Article article1 = Article.builder()
                .title("제목1")
                .text("내용")
                .build();

        articleRepository.save(article1);

        //when
        List<Article> findByTitle = articleRepository.findByTitle("제목1");

        //then
        assertThat(findByTitle.get(0)).isEqualTo(article1);
        assertThat(findByTitle.size()).isEqualTo(1);
    }

    @DisplayName("글 서비스 클래스를 통한 저장 테스트")
    @Transactional
    @Test
    void articleServiceAddTest() {
        AddArticle addArticle = AddArticle.builder()
                .title("제목")
                .text("내용")
                .build();

        articleService.saveArticle(addArticle);

        assertThat(articleRepository.findAll().size()).isEqualTo(1);
        assertThat(articleRepository.findAll().get(0).getTitle()).isEqualTo("제목");
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

        EditArticle editArticle = EditArticle.builder()
                .title("제목이지롱")
                .text("글이지롱")
                .build();

        articleRepository.save(article);

        articleService.editArticle(article.getAId(), editArticle);

        assertThat(article.getTitle()).isEqualTo("제목이지롱");
        assertThat(article.getText()).isEqualTo("글이지롱");

        //조회 후 비교
        Article findArticle = articleRepository.findById(article.getAId())
                .orElseThrow(() -> new NullArticleException("글이 없습니다."));

        assertThat(findArticle.getTitle()).isEqualTo("제목이지롱");
        assertThat(findArticle.getText()).isEqualTo("글이지롱");

    }

    @DisplayName("글 삭제 테스트")
    @Transactional
    @Test
    void articleDelete() throws NullArticleException {
        Article article = Article.builder()
                .title("제목입니다.")
                .text("내용입니다.")
                .build();

        articleRepository.save(article);

        articleService.deleteArticle(article.getAId());

        assertThat(articleRepository.findAll().size()).isEqualTo(0);
    }
}