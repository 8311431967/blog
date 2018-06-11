package kunrong.kr.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * General hostel world exception
 */
@Getter
@Setter
@AllArgsConstructor
public class BlogException extends Exception{
    private int code;
    private String message;
}
