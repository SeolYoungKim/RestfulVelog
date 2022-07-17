package ToyProject.RestfulVelog.web.request;

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
}
