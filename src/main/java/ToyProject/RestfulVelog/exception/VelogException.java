package ToyProject.RestfulVelog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class VelogException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public VelogException(String message) {
        super(message);
    }

    public VelogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
