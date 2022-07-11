package ToyProject.RestfulVelog.domain.repository;

import ToyProject.RestfulVelog.domain.Article;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<Article> findByTitle(String title);
}
