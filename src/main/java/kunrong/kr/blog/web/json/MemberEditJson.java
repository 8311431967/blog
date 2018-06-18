package kunrong.kr.blog.web.json;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Data
public class MemberEditJson {
    @NotNull
    private int memberId;
    @Length(max = 255,message = "max length of password is 255")
    private String password;
    @NotNull
    private String avatar;
    @Pattern(regexp = "male|female")
    private String gender;
    private String description;
}
