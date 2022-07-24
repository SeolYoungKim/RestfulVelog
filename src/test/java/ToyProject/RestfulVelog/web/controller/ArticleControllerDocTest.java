package ToyProject.RestfulVelog.web.controller;

import ToyProject.RestfulVelog.domain.Article;
import ToyProject.RestfulVelog.repository.ArticleRepository;
import ToyProject.RestfulVelog.web.request.AddArticle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.velog.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class ArticleControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("hello print")
    void test1() throws Exception {
        this.mockMvc.perform(get("/")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("index"));
    }

    @Test
    @DisplayName("read 1 article")
    void test2() throws Exception {

        Article article = Article.builder()
                .title("제목")
                .text("내용")
                .build();

        articleRepository.save(article);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/article/{id}", 1L)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("readArticle",
                        pathParameters(
                                parameterWithName("id").description("게시글의 ID 입니다.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글의 ID 입니다."),
                                fieldWithPath("title").description("게시글의 제목 입니다."),
                                fieldWithPath("text").description("게시글의 내용 입니다.")
                        )
                ));
    }

    @Test
    @DisplayName("write article")
    void test3() throws Exception {

        AddArticle addArticle = AddArticle.builder()
                .title("제목1")
                .text("내용1")
                .build();

        String json = objectMapper.writeValueAsString(addArticle);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/write")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("addArticle",
                        requestFields(
                                fieldWithPath("title").description("등록하고자 하는 제목입니다.")
                                        .attributes(key("constraint").value("제목에 바보는 들어가면 안됩니다.")),
                                fieldWithPath("text").description("등록하고자 하는 내용입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글의 ID 입니다."),
                                fieldWithPath("title").description("게시글의 제목입니다."),
                                fieldWithPath("text").description("게시글의 내용입니다.")
                        )
                ));
    }
}


