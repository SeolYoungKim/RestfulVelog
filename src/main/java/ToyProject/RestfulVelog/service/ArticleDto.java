package ToyProject.RestfulVelog.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter @Setter
public class ArticleDto {

    @NotBlank
    private String title;

    @NotBlank
    private String text;
}
