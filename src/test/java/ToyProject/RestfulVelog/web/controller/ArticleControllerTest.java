package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import ToyProject.RestfulVelog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void clear() {
        articleRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("/ 요청 시 Hi! 를 출력한다.")
    void printHi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hi!"));

    }

    @DisplayName("/article 에 post 요청 시 글이 저장된다")
    @Test
    void saveArticle() throws Exception {

        String json = "{\"title\": \"제목입니다\", \"text\" : \"본문입니다\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/write")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("/article/1 에 get 요청 시 id로 글이 조회된다.")
    @Test
    void readArticle() throws Exception {

        Article article = articleRepository.save(Article.builder()
                .text("text")
                .title("title")
                .build());

        Long aId = article.getAId();

//        // service 구현체가 없어도 테스트 가능! -> mock bean을 이용한 테스트 방법
//        given(this.articleService.findById(1L))  // findById 메소드에 parameter=1이 입력될 경우
//                .willReturn(article);  // article 객체를 리턴한다.


        // 글이 있을 때
        mockMvc.perform(MockMvcRequestBuilders.get("/article/" + aId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(aId))
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.text").value(article.getText()))
                .andDo(print());

        // 글이 없을 때
        mockMvc.perform(MockMvcRequestBuilders.get("/article/2343")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.causedBy.pathVariable").value("조회할 글이 없습니다."))
                .andDo(print());
    }

    @DisplayName("/articles 글 여러개를 조회한다.")
    @Test
    void readAllArticles() throws Exception {
        //save를 테스트 코드 내에서 했기 때문에, Transactional 어노테이션이 필요없음. 외부에 세이브 메서드가 있으면 Transactional 어노테이션을 달아주자.
        List<Article> requestArticles = IntStream.range(1, 31)
                .mapToObj(i -> Article.builder()
                        .title("제목 " + i)
                        .text("내용 " + i)
                        .build())
                .collect(Collectors.toList());

        articleRepository.saveAll(requestArticles);

        mockMvc.perform(MockMvcRequestBuilders.get("/articles?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].title").value("제목 30"))
                .andExpect(jsonPath("$[0].text").value("내용 30"))
                .andDo(print());

    }

    @DisplayName("/articles 페이지를 0으로 요청해도, 첫 페이지를 가져온다.")
    @Test
    void pageZeroTest() throws Exception {
        //save를 테스트 코드 내에서 했기 때문에, Transactional 어노테이션이 필요없음. 외부에 세이브 메서드가 있으면 Transactional 어노테이션을 달아주자.
        List<Article> requestArticles = IntStream.range(1, 31)
                .mapToObj(i -> Article.builder()
                        .title("제목 " + i)
                        .text("내용 " + i)
                        .build())
                .collect(Collectors.toList());

        articleRepository.saveAll(requestArticles);

        mockMvc.perform(MockMvcRequestBuilders.get("/articles?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].title").value("제목 30"))
                .andExpect(jsonPath("$[0].text").value("내용 30"))
                .andDo(print());

    }

    @DisplayName(("/page 조회 시, 페이징 처리가 된 결과를 얻을 수 있다."))
    @Test
    void pageOfArticles() throws Exception {

        List<Article> articleList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            articleList.add(
                    Article.builder()
                            .title("title" + i)
                            .text("text" + i)
                            .build()
            );
        }

        articleRepository.saveAll(articleList);

        mockMvc.perform(MockMvcRequestBuilders.get("/page")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.content.[0].title").value("title29"))
                .andExpect(jsonPath("$.content.[0].text").value("text29"))
                .andDo(print());
    }

    @Transactional
    @DisplayName("/article/{id}/edit > 수정 테스트")
    @Test
    void editArticle() throws Exception {
        Article article = Article.builder()
                .title("제목임")
                .text("본문임")
                .build();

        articleRepository.save(article);
        Long aId = article.getAId();

        String json = "{\"title\": \"제목입니다\", \"text\" : \"본문입니다\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/article/" + aId + "/edit")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목입니다"))
                .andExpect(jsonPath("$.text").value("본문입니다"))
                .andDo(print());

    }

    @DisplayName("ArticleDto 검증 테스트")
    @Test
    void validationArticleDto() throws Exception {

        String json = "{\"title\":\"\",\"text\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/write")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.causedBy.title").value("제목을 입력하세요."))
                .andExpect(jsonPath("$.causedBy.text").value("본문을 입력하세요."))
                .andDo(print());

    }
}


















