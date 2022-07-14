package ToyProject.RestfulVelog.service;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import ToyProject.RestfulVelog.exception.NullArticleException;
import ToyProject.RestfulVelog.request.RequestArticleDto;
import ToyProject.RestfulVelog.response.ResponseArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ResponseArticleDto saveArticle(RequestArticleDto requestArticleDto) {
        Article article = Article.builder()
                .title(requestArticleDto.getTitle())
                .text(requestArticleDto.getText())
                .build();

        articleRepository.save(article);

        return ResponseArticleDto.builder()
                .title(article.getTitle())
                .text(article.getText())
                .build();
    }

    public ResponseArticleDto editArticle(Long id, RequestArticleDto requestArticleDto) throws NullArticleException {
        Article findArticle = articleRepository.findById(id)
                .orElseThrow(() -> new NullArticleException("글이 없습니다."));  //id로 객체를 찾는다

        findArticle.edit(requestArticleDto.getTitle(), requestArticleDto.getText());

        Article editedArticle = articleRepository.save(findArticle);

        return ResponseArticleDto.builder()
                .title(editedArticle.getTitle())
                .text(editedArticle.getText())
                .build();
    }

    public ResponseArticleDto findById(Long id) throws NullArticleException {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NullArticleException("글이 없습니다."));

        return ResponseArticleDto.builder()
                .title(article.getTitle())
                .text(article.getText())
                .build();
    }

    public List<ResponseArticleDto> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(article -> ResponseArticleDto.builder()
                                .title(article.getTitle())
                                .text(article.getText())
                                .build())
                .collect(Collectors.toList());
    }

    public List<ResponseArticleDto> findByTitle(String title) {
        return articleRepository.findByTitle(title).stream()
                .map(article -> ResponseArticleDto.builder()
                        .title(article.getTitle())
                        .text(article.getText())
                        .build())
                .collect(Collectors.toList());
    }


}











