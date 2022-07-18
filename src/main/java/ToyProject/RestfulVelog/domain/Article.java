package ToyProject.RestfulVelog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    //TODO: 이것도 좋긴 한데, 나중에 더 많아질 파라미터를 대비해보자.
//    public void edit(EditArticle editArticle) {  // 굳이 EditArticle에서 가져오는게 아니라 빌더를 더 추가하는 이유는 뭘까?
//        this.title = editArticle.getTitle();
//        this.text = editArticle.getText();
//    }

    //아래는 호돌맨님이 사용하시는 패턴이다
    public ArticleEditor.ArticleEditorBuilder toEditor() {
        return ArticleEditor.builder()
                .title(this.title)
                .text(this.text);  // builder를 그대로 넘겨준다.
    }

    public void edit(ArticleEditor articleEditor) {
        this.title = articleEditor.getTitle();
        this.text = articleEditor.getText();
    }
}
