package kunrong.kr.blog.bl.vo;

import kunrong.kr.blog.data.entity.MemberEntity;
import kunrong.kr.blog.util.enums.Gender;
import kunrong.kr.blog.util.enums.MemberState;
import kunrong.kr.blog.util.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class MemberVo {
    private int id;
    private String name;
    private String phone;
    private String avatar;
    private Gender gender;
    private UserType type;
    private Timestamp createdAt;
    private Timestamp updateAt;
    private MemberState state;
    private Integer level;
    private Integer score;
    private String description;
    private Integer remain;
    private byte valid;
    private Date activeDate;

    public MemberVo(MemberEntity memberEntity) {
        BeanUtils.copyProperties(memberEntity,this,"deletedAt","password","authorityEntities",
                "accountEntities","messageEntities","checkEntities","reserveEntities");
    }
}
