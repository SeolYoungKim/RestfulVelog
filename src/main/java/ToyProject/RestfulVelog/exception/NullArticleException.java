package ToyProject.RestfulVelog.exception;

public class NullArticleException extends Exception{
    public NullArticleException() {
        super();
    }

    public NullArticleException(String message) {
        super(message);
    }

    public NullArticleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullArticleException(Throwable cause) {
        super(cause);
    }

    protected NullArticleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
