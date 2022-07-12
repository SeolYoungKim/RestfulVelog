package ToyProject.RestfulVelog.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter @Setter
public class ArticleDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "본문을 입력하세요.")
    private String text;
}
