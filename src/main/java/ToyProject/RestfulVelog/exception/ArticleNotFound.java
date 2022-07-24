package ToyProject.RestfulVelog.exception;

/**
 * status -> 404
 */

public class ArticleNotFound extends VelogException {

    private static final String MESSAGE = "조회할 글이 없습니다.";

    public ArticleNotFound() {
        super(MESSAGE);
    }

    public ArticleNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
