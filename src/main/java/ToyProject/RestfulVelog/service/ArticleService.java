package ToyProject.RestfulVelog.service;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.ArticleEditor;
import ToyProject.RestfulVelog.repository.ArticleRepository;
import ToyProject.RestfulVelog.exception.ArticleNotFound;
import ToyProject.RestfulVelog.web.request.AddArticle;
import ToyProject.RestfulVelog.web.request.ArticleSearch;
import ToyProject.RestfulVelog.web.request.EditArticle;
import ToyProject.RestfulVelog.web.response.ResponseArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ResponseArticleDto saveArticle(AddArticle addArticle) {
        Article article = Article.builder()
                .title(addArticle.getTitle())
                .text(addArticle.getText())
                .build();

        articleRepository.save(article);

        return new ResponseArticleDto(article);
    }

    @Transactional
    public ResponseArticleDto editArticle(Long id, EditArticle editArticle) throws ArticleNotFound {
        Article findArticle = articleRepository.findById(id)
                .orElseThrow(ArticleNotFound::new);  //id로 객체를 찾는다

//        findArticle.edit(editArticle);

        // @Transcational을 걸어주면, 아래 저장 메서드를 불러오지 않아도 저장이 된다. TODO: @Transactional 공부
//        Article editedArticle = articleRepository.save(findArticle);

        ArticleEditor.ArticleEditorBuilder articleEditorBuilder = findArticle.toEditor();

        ArticleEditor articleEditor = articleEditorBuilder
                .title(editArticle.getTitle())
                .text(editArticle.getText())
                .build();

        findArticle.edit(articleEditor);

        return new ResponseArticleDto(findArticle);
    }

    public ResponseArticleDto findById(Long id) throws ArticleNotFound {
        Article article = articleRepository.findById(id)
                .orElseThrow(ArticleNotFound::new);

        return new ResponseArticleDto(article);
    }


    //TODO: 호돌맨님 강의 들은 후, 혼자 복습 및 사용해보기.
    public List<ResponseArticleDto> getList(ArticleSearch articleSearch) {

//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "aId"));

        return articleRepository.getPageList(articleSearch).stream()
                .map(ResponseArticleDto::new)
                .collect(Collectors.toList());
    }

    // builder를 사용해서 매핑하여 반환하는 예제. 이렇게 작성하면 긴 코드가 상당히 반복적으로 작업되기 때문에 피곤한다. 위와 같이 변경하자.
    public List<ResponseArticleDto> findByTitle(String title) {
        return articleRepository.findByTitle(title).stream()
                .map(ResponseArticleDto::new)
                .collect(Collectors.toList());
    }


    //내가 구글링으로 헤딩하며 겨우 구현했던 페이징 처리 -> querydsl로 쉽게구현하자.
    public Page<ResponseArticleDto> findAllToPage(Pageable pageable) {

        List<ResponseArticleDto> collect = articleRepository.findAll(pageable).get()
                .map(ResponseArticleDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(collect);
    }

    // 삭제
    public String deleteArticle(Long id) throws ArticleNotFound {

        Article findArticle = articleRepository.findById(id)
                .orElseThrow(ArticleNotFound::new);

        articleRepository.delete(findArticle);

        return "delete-ok";
    }

}











