package ToyProject.RestfulVelog.web.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResult {

    private final String code;
    private final String message;
    private final Map<String, String> causedBy;

    @Builder
    public ErrorResult(String code, String message, Map<String, String> causedBy) {
        this.code = code;
        this.message = message;
        this.causedBy = causedBy != null ? causedBy : new HashMap<>();
    }

    public void causedByField(String filed, String message) {
        this.causedBy.put(filed, message);
    }
}
