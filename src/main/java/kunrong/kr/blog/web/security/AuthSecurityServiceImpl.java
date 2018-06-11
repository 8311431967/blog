package kunrong.kr.blog.web.security;

import kunrong.kr.blog.data.dao.UserDao;
import kunrong.kr.blog.data.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthSecurityServiceImpl implements AuthSecurityService {
    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findByUserName(s);
        if (userEntity == null) throw new UsernameNotFoundException("Cannot find user.");
        List<GrantedAuthority> authorities = userEntity.getAuthorityEntities().stream()
                .map(authorityEntity -> new SimpleGrantedAuthority(authorityEntity.getName()))
                .collect(Collectors.toList());
        return new BlogUser(userEntity.getName(),userEntity.getPassword(),true,
                true,true,true,authorities);
    }
}
