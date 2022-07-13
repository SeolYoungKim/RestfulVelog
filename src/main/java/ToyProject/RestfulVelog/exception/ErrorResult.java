package ToyProject.RestfulVelog.exception;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorResult {

    private String code;
    private String message;
    private final Map<String, String> causedBy = new HashMap<>();

    @Builder
    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void causedByField(String filed, String message) {
        this.causedBy.put(filed, message);
    }
}
