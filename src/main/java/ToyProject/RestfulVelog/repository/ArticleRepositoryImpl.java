package ToyProject.RestfulVelog.repository;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.web.request.ArticleSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static ToyProject.RestfulVelog.domain.QArticle.article;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> findByTitle(String title) {
        return jpaQueryFactory.selectFrom(article)
                .where(article.title.eq(title))
                .fetch();
    }

    @Override
    public List<Article> getPageList(ArticleSearch articleSearch) {
        return jpaQueryFactory.selectFrom(article)
                .limit(articleSearch.getSize())
                .offset(articleSearch.getOffset())
                .orderBy(article.aId.desc())
                .fetch();
    }
}
