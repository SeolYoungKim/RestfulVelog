package ToyProject.RestfulVelog.domain.repository;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.web.request.ArticleSearch;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<Article> findByTitle(String title);

    List<Article> getPageList(ArticleSearch articleSearch);
}
