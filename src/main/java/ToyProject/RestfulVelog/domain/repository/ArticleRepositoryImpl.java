package ToyProject.RestfulVelog.domain.repository;

import ToyProject.RestfulVelog.domain.Article;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> findByTitle(String title) {
        return new ArrayList<>();
//                jpaQueryFactory.selectFrom(article)
//                .where(article.title.eq(title))
//                .fetch();
    }
}
