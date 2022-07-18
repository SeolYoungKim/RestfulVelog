package ToyProject.RestfulVelog.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class EditArticle {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String text;

    @Builder
    public EditArticle(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
