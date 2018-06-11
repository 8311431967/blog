package kunrong.kr.blog.web.handler;

import kunrong.kr.blog.bl.vo.ResultVo;
import kunrong.kr.blog.exception.BlogException;
import kunrong.kr.blog.util.constant.ErrorCode;
import kunrong.kr.blog.util.constant.MessageConstant;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
@ControllerAdvice(basePackages = "kunrong.kr.blog.web.controller")
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public ResultVo<List> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) throws Exception {
        List<ArgInvalidResult> invalidArguments = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            ArgInvalidResult invalidArgument = new ArgInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        });
        return new ResultVo<>(ErrorCode.WRONG_PARAMETER, MessageConstant.WRONG_PARAMETER,invalidArguments);
    }

    @ExceptionHandler(value = BlogException.class)
    public ResultVo<String> hostelExceptionHandler(BlogException exception) throws Exception {
        return new ResultVo<>(exception.getCode(),exception.getMessage(),exception.getLocalizedMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResultVo<String> accessDeniedExceptionHandler(AccessDeniedException exception)
            throws Exception {
        return new ResultVo<>(ErrorCode.AUTHORITY_FORBIDDEN,MessageConstant.AUTHORITY_FORBIDDEN,exception.getReason()
                +":"+exception.getMessage());
    }
}
