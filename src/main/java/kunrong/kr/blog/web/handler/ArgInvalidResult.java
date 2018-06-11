package kunrong.kr.blog.web.handler;

import lombok.Data;

/**
 * Describes argument invalid error
 * @author cuihao
 */
@Data
public class ArgInvalidResult {
    private String field;
    private Object rejectedValue;
    private String defaultMessage;
}
