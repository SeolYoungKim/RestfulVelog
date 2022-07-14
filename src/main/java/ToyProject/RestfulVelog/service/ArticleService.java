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

        return new ResponseArticleDto(article);
    }

    public ResponseArticleDto editArticle(Long id, RequestArticleDto requestArticleDto) throws NullArticleException {
        Article findArticle = articleRepository.findById(id)
                .orElseThrow(() -> new NullArticleException("글이 없습니다."));  //id로 객체를 찾는다

        findArticle.edit(requestArticleDto.getTitle(), requestArticleDto.getText());

        Article editedArticle = articleRepository.save(findArticle);

        return new ResponseArticleDto(editedArticle);
    }

    public ResponseArticleDto findById(Long id) throws NullArticleException {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NullArticleException("글이 없습니다."));

        return new ResponseArticleDto(article);
    }

    public List<ResponseArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ResponseArticleDto::new)
                .collect(Collectors.toList());
    }

    // builder를 사용해서 매핑하여 반환하는 예제. 이렇게 작성하면 긴 코드가 상당히 반복적으로 작업되기 때문에 피곤한다. 위와 같이 변경하자.
    public List<ResponseArticleDto> findByTitle(String title) {
        return articleRepository.findByTitle(title).stream()
                .map(ResponseArticleDto::new)
                .collect(Collectors.toList());
    }


}











