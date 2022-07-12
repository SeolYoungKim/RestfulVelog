package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.domain.repository.ArticleRepository;
import ToyProject.RestfulVelog.service.ArticleDto;
import ToyProject.RestfulVelog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

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

        mockMvc.perform(MockMvcRequestBuilders.post("/article")
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

        // service 구현체가 없어도 테스트 가능!
        given(this.articleService.findById(1L))  // findById 메소드에 parameter=1이 입력될 경우
                .willReturn(article);  // article 객체를 리턴한다.

        String json = "{\"title\":\"title\",\"text\":\"text\",\"aid\":null}";

        mockMvc.perform(MockMvcRequestBuilders.get("/article/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(json))
                .andDo(print());
    }

    @DisplayName("ArticleDto 검증 테스트")
    @Test
    void validationArticleDto() throws Exception {

        String json = "{\"title\":\"\",\"text\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/article")
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


















