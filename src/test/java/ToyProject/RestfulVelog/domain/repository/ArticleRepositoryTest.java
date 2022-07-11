package ToyProject.RestfulVelog.domain.repository;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

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

}