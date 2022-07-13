package ToyProject.RestfulVelog.service;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import ToyProject.RestfulVelog.exception.NullArticleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void editArticle(Long id, ArticleDto articleDto) throws NullArticleException {
        Article findArticle = articleRepository.findById(id)
                .orElseThrow(() -> new NullArticleException("글이 없습니다."));  //id로 객체를 찾는다

        findArticle.edit(articleDto.getTitle(), articleDto.getText());
        articleRepository.save(findArticle);
    }

    //TODO : Article -> 응답객체를 따로 만들어서 응답을 해보자.
    public Article findById(Long id) throws NullArticleException {
        return articleRepository.findById(id).orElseThrow(() -> new NullArticleException("글이 없습니다."));
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }


}
