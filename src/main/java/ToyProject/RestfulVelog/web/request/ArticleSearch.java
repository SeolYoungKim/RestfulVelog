package ToyProject.RestfulVelog.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;

@Builder
@Getter @Setter
public class ArticleSearch {

    public static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;  // 기본값을 넣어주거나,

//    //TODO : 코드 감상하기(?)
//    public ArticleSearch(Integer pageNum, Integer pageSize) {
//        this.pageNum = pageNum == null ? 1 : pageNum;  // 이렇게 하면 됨.
//        this.pageSize = pageSize;
//    }

    public long getOffset() {  // page가 변할 때 마다 offset이 변동되므로, 페이지 별로 적절한 아티클을 가져올 수 있다.
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}
