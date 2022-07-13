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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        articleRepository.deleteAll();
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

        Article article = Article.builder()
                .text("text")
                .title("title")
                .build();

//        // service 구현체가 없어도 테스트 가능! -> mock bean을 이용한 테스트 방법
//        given(this.articleService.findById(1L))  // findById 메소드에 parameter=1이 입력될 경우
//                .willReturn(article);  // article 객체를 리턴한다.

        articleRepository.save(article);
        Long aId = article.getAId();

        String json = "{\"title\":\"title\",\"text\":\"text\",\"aid\":" + aId +"}";

        // 글이 있을 때
        mockMvc.perform(MockMvcRequestBuilders.get("/article/" + aId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(json))
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
        Article article1 = Article.builder()
                .title("title1")
                .text("text1")
                .build();

        Article article2 = Article.builder()
                .title("title2")
                .text("text2")
                .build();

        articleRepository.save(article1);
        articleRepository.save(article2);

        mockMvc.perform(MockMvcRequestBuilders.get("/articles")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title[0]").value("title1"))
//                .andExpect(jsonPath("$.text[0]").value("text1"))
//                .andExpect(jsonPath("$.title[1]").value("title2"))
//                .andExpect(jsonPath("$.text[1]").value("text2"))
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
                .andExpect(jsonPath("$.aid").value(aId))
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


















