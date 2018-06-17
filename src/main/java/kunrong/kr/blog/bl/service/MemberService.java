package kunrong.kr.blog.bl.service;

import kunrong.kr.blog.bl.vo.MemberVo;
import kunrong.kr.blog.bl.vo.ResultVo;
import kunrong.kr.blog.exception.BlogException;
import kunrong.kr.blog.util.enums.Gender;
import org.springframework.data.domain.Page;

public interface MemberService {
    /**
     * Get list of hotel
     * @param page page number
     * @param pageSize maxSize
     * @return List of {@link }
     */
    Page<MemberVo> getMemberList(int page, int pageSize);

    /**
     * Get member basic person info, not including extra info.
     * @param memberId user id of member
     * @return {@link MemberVo}
     */
    MemberVo getMemberInfo(int memberId);

    /**
     * Create member
     * @return {@link MemberVo}
     */
    MemberVo createMember(String name, String password, String phone, String avatar,
                          Gender gender, String description);

    /**
     * Edit member
     * @return {@link MemberVo}
     */

    MemberVo editMember(int memberId, String password, String avatar, Gender gender, String description);

    /**
     * Activate member authority.
     * @param memberId member id
     * @param accountId account id
     * @param money activate money
     * @return {@link ResultVo <MemberVo>}
     */
    ResultVo<MemberVo> transferToRemain(int memberId, int accountId, int money) throws BlogException;

    /**
     * Stop member authority
     * @param memberId member id
     * @return {@link ResultVo <MemberVo>}
     */
    ResultVo<MemberVo> stopMember(int memberId) throws BlogException;

    /**
     * Pause member authority
     * @param memberId member id
     * @return {@link ResultVo <MemberVo>}
     */
    ResultVo<MemberVo> pauseMember(int memberId);

    /**
     * Exchange score to remain money
     * @param score score to exchange
     * @return {@link ResultVo <MemberVo>}
     */
    ResultVo<MemberVo> exchangeScore(int memberId, int score) throws BlogException;

    /**
     * Use member remain to pay
     * @param memberId member user id
     * @param payNum pay number
     * @return {@link ResultVo <MemberVo>}
     */
    ResultVo<MemberVo> memberPay(int memberId, int payNum) throws BlogException;

    /**
     * Check member active date
     */
    void checkMemberState();
}
