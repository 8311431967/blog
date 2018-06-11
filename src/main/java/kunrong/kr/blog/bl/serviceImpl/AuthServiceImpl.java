package kunrong.kr.blog.bl.serviceImpl;


import kunrong.kr.blog.bl.service.AuthService;
import kunrong.kr.blog.bl.vo.ResultVo;
import kunrong.kr.blog.bl.vo.UserVo;
import kunrong.kr.blog.data.dao.UserDao;
import kunrong.kr.blog.data.entity.UserEntity;
import kunrong.kr.blog.util.constant.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Auth service impl
 * @author cuihao
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserDao userDao;

    /**
     * Find user by username
     *
     * @param userName user name
     * @return {@link UserVo}
     */
    @Override
    public UserVo findByUserName(String userName) {
        UserEntity userEntity = userDao.findByUserName(userName);
        if (userEntity!=null) {
            return new UserVo(userEntity);
        }
        return null;
    }

    /**
     * Find user by userId
     *
     * @param userId user id
     * @return {@link UserVo}
     */
    @Override
    public UserVo findByUserId(int userId) {
        UserEntity userEntity = userDao.findById(userId);
        if (userEntity!=null) {
            return new UserVo(userEntity);
        }
        return null;
    }

    /**
     * login to system
     *
     * @param userName user name
     * @param password password
     * @return {@link ResultVo < UserVo >}
     */
    @Override
    public ResultVo<UserVo> login(String userName, String password) {
        UserEntity userEntity = userDao.findByUserName(userName);
        ResultVo<UserVo> userVoResultVo = new ResultVo<>(null);
        if (userEntity == null) {
            userVoResultVo.setMessage("Cannot find user.");
        } else {
            if (!userEntity.getPassword().equals(password)) {
                userVoResultVo.setMessage("Error password.");
            } else {
                userVoResultVo.setCode(ErrorCode.SUCCESS);
                userVoResultVo.setMessage("Login succeed.");
                userVoResultVo.setData(new UserVo(userEntity));
            }
        }
        return userVoResultVo;
    }

    /**
     * Verify the operation changes the user's own profile
     *
     * @param userId user id to be changed
     * @return is self
     */
    @Override
    public boolean isSelf(int userId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userDao.findByUserName(userDetails.getUsername());
        return userEntity!=null && userEntity.getId() == userId;
    }


}
