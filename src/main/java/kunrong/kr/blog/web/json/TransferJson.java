package kunrong.kr.blog.web.json;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data
public class TransferJson {
    @NotNull(message = "member id cannot be null.")
    private int memberId;
    @NotNull(message = "account id cannot be null.")
    private int accountId;
    @Min(value = 0,message = "transfer money must more than 0")
    private int money;
}
