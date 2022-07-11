package ToyProject.RestfulVelog.service;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void saveArticle(ArticleDto articleDto) {
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .text(articleDto.getText())
                .build();

        articleRepository.save(article);
    }
}
