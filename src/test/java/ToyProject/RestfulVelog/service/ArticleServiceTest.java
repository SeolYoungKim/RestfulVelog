package ToyProject.RestfulVelog.service;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @DisplayName("글 전부 조회")
    @Transactional
    @Test
    void readAllArticle() {
        Article article1 = Article.builder()
                .title("제목1")
                .text("글1")
                .build();

        Article article2 = Article.builder()
                .title("제목2")
                .text("글2")
                .build();

        articleRepository.save(article1);
        articleRepository.save(article2);

        List<Article> all = articleService.findAll();

        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0)).isEqualTo(article1);
        assertThat(all.get(1)).isEqualTo(article2);
    }

    @DisplayName("글 수정 테스트")
    @Transactional
    @Test
    void articleEdit() throws Exception {
        Article article = Article.builder()
                .title("제목")
                .text("글")
                .build();

        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle("제목이지롱");
        articleDto.setText("글이지롱");

        articleRepository.save(article);

        articleService.editArticle(article.getAId(), articleDto);

        assertThat(article.getTitle()).isEqualTo("제목이지롱");
        assertThat(article.getText()).isEqualTo("글이지롱");

    }

}