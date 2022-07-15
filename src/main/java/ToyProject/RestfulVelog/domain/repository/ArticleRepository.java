package ToyProject.RestfulVelog.domain.repository;

import ToyProject.RestfulVelog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

//    Page<Article> findAll(Pageable pageable);
}
