package ToyProject.RestfulVelog.service;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ArticleDto {

    @NotBlank
    private String title;

    @NotBlank
    private String text;
}
