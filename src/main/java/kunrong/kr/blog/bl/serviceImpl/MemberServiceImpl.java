package kunrong.kr.blog.bl.serviceImpl;

import kunrong.kr.blog.bl.service.AuthService;
import kunrong.kr.blog.bl.service.MemberService;
import kunrong.kr.blog.bl.vo.MemberVo;
import kunrong.kr.blog.bl.vo.ResultVo;
import kunrong.kr.blog.data.dao.AuthorityDao;
import kunrong.kr.blog.data.dao.MemberDao;
import kunrong.kr.blog.data.entity.AuthorityEntity;
import kunrong.kr.blog.data.entity.MemberEntity;
import kunrong.kr.blog.exception.BlogException;
import kunrong.kr.blog.util.constant.AuthorityConstant;
import kunrong.kr.blog.util.constant.ErrorCode;
import kunrong.kr.blog.util.constant.MemberConstant;
import kunrong.kr.blog.util.constant.MessageConstant;
import kunrong.kr.blog.util.enums.Gender;
import kunrong.kr.blog.util.enums.MemberState;
import kunrong.kr.blog.util.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberDao memberDao;

    @Resource
    private AuthorityDao authorityDao;


    @Resource
    private AuthService authService;


    /**
     * Get list of hotel
     *
     * @param page     page number
     * @param pageSize maxSize
     * @return List of {@link }
     */
    @Override
    public Page<MemberVo> getMemberList(int page, int pageSize) {
        Page<MemberEntity> memberEntities = memberDao.findAll(page,pageSize,"id", Sort.Direction.ASC);
        return new PageImpl<>(memberEntities.getContent().stream().map(MemberVo::new).collect(Collectors.toList()),
                memberEntities.nextPageable(),memberEntities.getTotalElements());
    }

    /**
     * Get member basic person info, not including extra info.
     *
     * @param memberId user id of member
     * @return {@link MemberVo}
     */
    @Override
    public MemberVo getMemberInfo(int memberId) {
        return new MemberVo(memberDao.findById(memberId));
    }

    /**
     * Create member
     *
     * @return {@link MemberVo}
     */
    @Override
    public MemberVo createMember(String name, String password, String phone,
                                 String avatar, Gender gender, String description) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(name);
        memberEntity.setPassword(password);
        memberEntity.setPhone(phone);
        memberEntity.setAvatar(avatar);
        memberEntity.setType(UserType.member);
        memberEntity.setValid((byte) 0);
        memberEntity.setGender(gender);
        memberEntity.setDescription(description);
        memberEntity.setState(MemberState.newly);
        memberEntity.setLevel(0);
        memberEntity.setScore(0);
        memberEntity.setRemain(0);
        memberEntity.setAuthorityEntities(authorityDao.findMemberPause());
        MemberEntity memberEntitySaved = memberDao.save(memberEntity);
        return new MemberVo(memberDao.findById(memberEntitySaved.getId()));
    }

    /**
     * Edit member
     *
     * @param memberId member id
     * @param password password
     * @param avatar avatar
     * @param gender gender
     * @param description description
     * @return {@link MemberVo}
     */
    @Override
    public MemberVo editMember(int memberId, String password, String avatar, Gender gender, String description) {
        MemberEntity memberEntity = memberDao.findById(memberId);
        memberEntity.setPassword(password);
        memberEntity.setAvatar(avatar);
        memberEntity.setGender(gender);
        memberEntity.setDescription(description);
        return new MemberVo(memberDao.save(memberEntity));
    }

    @Override
    public ResultVo<MemberVo> transferToRemain(int memberId, int accountId, int money) throws BlogException {
        return null;
    }


    /**
     * Stop member authority
     *
     * @param memberId member id
     * @return {@link ResultVo < MemberVo >}
     */
    @Override
    public ResultVo<MemberVo> stopMember(int memberId) throws BlogException {
        MemberEntity memberEntity = memberDao.findById(memberId);
        if (memberEntity==null) throw new BlogException(ErrorCode.MEMBER_NOT_FOUND, MessageConstant.MEMBER_NOT_FOUND);
        memberEntity.setState(MemberState.stop);
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        authorityEntities.add(authorityDao.findByName(AuthorityConstant.USER_BASE));
        memberEntity.setAuthorityEntities(authorityEntities);
        return new ResultVo<>(ErrorCode.SUCCESS, MessageConstant.SUCCESS,new MemberVo(memberDao.save(memberEntity)));
    }

    /**
     * Pause member authority
     *
     * @param memberId member id
     * @return {@link ResultVo < MemberVo >}
     */
    @Override
    public ResultVo<MemberVo> pauseMember(int memberId) {
        MemberEntity memberEntity = memberDao.findById(memberId);
        memberEntity.setState(MemberState.pause);
        memberEntity.setAuthorityEntities(authorityDao.findMemberPause());
        return new ResultVo<>(ErrorCode.SUCCESS, MessageConstant.SUCCESS,new MemberVo(memberDao.save(memberEntity)));
    }

    @Override
    public ResultVo<MemberVo> exchangeScore(int memberId, int score) throws BlogException {
        return null;
    }


    /**
     * Use member remain to pay
     *
     * @param memberId member user id
     * @param payNum   pay number
     * @return {@link ResultVo < MemberVo >}
     */
    @Override
    @Transactional
    public ResultVo<MemberVo> memberPay(int memberId, int payNum) throws BlogException {
        MemberEntity memberEntity = memberDao.findById(memberId);
        if (memberEntity == null) throw new BlogException(ErrorCode.MEMBER_NOT_FOUND, MessageConstant.MEMBER_NOT_FOUND);
        if (!authService.isSelf(memberId)) throw new BlogException(ErrorCode.MEMBER_CONFLICT, MessageConstant.MEMBER_CONFLICT);
        int remain = memberEntity.getRemain() - payNum;
        if (remain < 0) {
            throw new BlogException(ErrorCode.REMAIN_NOT_ENOUGH, MessageConstant.REMAIN_NOT_ENOUGH);
        }
        memberEntity.setRemain(remain);
        memberEntity.setScore(memberEntity.getScore()+payNum);
        memberEntity.setLevel(memberEntity.getLevel()+payNum);

        return new ResultVo<>(ErrorCode.SUCCESS, MessageConstant.SUCCESS,new MemberVo(memberDao.save(memberEntity)));
    }





    /**
     * Check member active date
     */
    @Override
    @Scheduled(cron = "0 0 2 * * ?")
    public void checkMemberState() {
        Date oneYearBefore = new Date(System.currentTimeMillis());
        oneYearBefore.setYear(oneYearBefore.getYear()- MemberConstant.ACTIVE_YEAR);
        Date twoYearBefore = new Date(System.currentTimeMillis());
        twoYearBefore.setYear(twoYearBefore.getYear()- MemberConstant.PAUSE_YEAR);
        List<MemberEntity> memberEntities = memberDao.findAll();
        memberEntities.stream()
                .filter(memberEntity -> memberEntity.getActiveDate()!=null)
                .filter(memberEntity -> memberEntity.getActiveDate().before(oneYearBefore)
                        && memberEntity.getState() == MemberState.active && memberEntity.getRemain()< MemberConstant.ACTIVE_REMAIN)
                .collect(Collectors.toList())
                .forEach(memberEntity -> pauseMember(memberEntity.getId()));
        memberEntities.stream()
                .filter(memberEntity -> memberEntity.getActiveDate()!=null)
                .filter(memberEntity -> memberEntity.getActiveDate().before(twoYearBefore)
                        && memberEntity.getState() == MemberState.pause)
                .collect(Collectors.toList())
                .forEach(memberEntity -> {
                    try {
                        stopMember(memberEntity.getId());
                    } catch (BlogException e) {
                        e.printStackTrace();
                    }
                });
    }

}
