package ToyProject.RestfulVelog.domain;

import ToyProject.RestfulVelog.web.request.EditArticle;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    private String title;

    @Lob
    private String text;

    @Builder
    public Article(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public void edit(EditArticle editArticle) {
        this.title = editArticle.getTitle();
        this.text = editArticle.getText();
    }
}
