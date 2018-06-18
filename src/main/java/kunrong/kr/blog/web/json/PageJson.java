package kunrong.kr.blog.web.json;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class PageJson {
    @NotNull
    private int page;
    @NotNull
    private int pageSize;
}
