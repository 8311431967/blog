package kunrong.kr.blog.bl.vo;

import kunrong.kr.blog.data.entity.AuthorityEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * AuthorityConstant entity vo
 * @author cuihao
 */
@Data
@NoArgsConstructor
public class AuthorityVo {
    private int id;
    private String name;
    private String description;
    public AuthorityVo(AuthorityEntity authorityEntity) {
        BeanUtils.copyProperties(authorityEntity,this);
    }
}
