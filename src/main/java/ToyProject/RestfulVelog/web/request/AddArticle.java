package ToyProject.RestfulVelog.web.request;

import ToyProject.RestfulVelog.exception.InvalidRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter @Setter
public class AddArticle {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "본문을 입력하세요.")
    private String text;

    @Builder
    public AddArticle(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public void validate() {
        if (this.title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }
}
