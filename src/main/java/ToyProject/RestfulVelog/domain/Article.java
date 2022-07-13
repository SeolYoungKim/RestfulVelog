package ToyProject.RestfulVelog.domain;

import lombok.*;

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

    public void edit(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
