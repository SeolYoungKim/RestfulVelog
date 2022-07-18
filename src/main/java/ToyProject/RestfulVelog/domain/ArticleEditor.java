package ToyProject.RestfulVelog.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleEditor {

    private final String title;
    private final String text;

    @Builder
    public ArticleEditor(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
