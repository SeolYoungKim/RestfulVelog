package ToyProject.RestfulVelog.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;
    private String title;
    private String text;

    @Builder
    public Article(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
