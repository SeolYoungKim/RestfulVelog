package ToyProject.RestfulVelog.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseArticleDto {

    private String title;

    private String text;

    @Builder
    public ResponseArticleDto(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
