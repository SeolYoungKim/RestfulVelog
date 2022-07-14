package ToyProject.RestfulVelog.web.response;

import ToyProject.RestfulVelog.domain.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseArticleDto {

    private final Long id;

    private final String title;

    private final String text;

    //Constructor overloading
    //코드를 간소화하기 위한 생성자 추가.
    public ResponseArticleDto(Article article) {
        this.id = article.getAId();
        this.title = article.getTitle();
        this.text = article.getText();
    }

    @Builder
    public ResponseArticleDto(Long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }
}
