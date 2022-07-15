package ToyProject.RestfulVelog.domain.repository;

import ToyProject.RestfulVelog.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO: @Repository를 안달아줘도 되는 이유가 뭘까?
public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    //TODO: 얘 이름 바꾸면 왜 빈이 형성되지 않는걸까?
    Page<Article> findAll(Pageable pageable);
}
