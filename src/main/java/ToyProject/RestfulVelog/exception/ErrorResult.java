package ToyProject.RestfulVelog.exception;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorResult {

    private String code;
    private String message;
    private Map<String, String> causedBy;

    @Builder
    public ErrorResult(String code, String message, Map<String, String> causedBy) {
        this.code = code;
        this.message = message;
        this.causedBy = causedBy;
    }
}
