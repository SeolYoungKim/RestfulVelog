package ToyProject.RestfulVelog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {

    private String error;
    private String massage;
}
