package kunrong.kr.blog.data.daoImpl;

import kunrong.kr.blog.data.dao.AuthorityDao;
import kunrong.kr.blog.data.entity.AuthorityEntity;
import kunrong.kr.blog.data.repository.crud.AuthorityRepository;
import kunrong.kr.blog.util.constant.AuthorityConstant;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthortyDaoImpl implements AuthorityDao {
    @Resource
    private AuthorityRepository authorityRepository;

    /**
     * Find authorities by id
     *
     * @param id id
     * @return {@link AuthorityEntity}
     */
    @Override
    public AuthorityEntity findById(int id) {
        return authorityRepository.findOne(id);
    }

    /**
     * Find all authorities
     *
     * @return list of {@link AuthorityEntity}
     */
    @Override
    public List<AuthorityEntity> findAll() {
        return (List<AuthorityEntity>) authorityRepository.findAll();
    }

    /**
     * Find authority by name
     *
     * @param name name of auth
     * @return {@link AuthorityEntity}
     */
    @Override
    public AuthorityEntity findByName(String name) {
        return authorityRepository.findByName(name);
    }

    /**
     * Find active members' authorities
     *
     * @return list of {@link AuthorityEntity}
     */
    @Override
    public List<AuthorityEntity> findMemberActive() {
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        authorityEntities.add(findByName(AuthorityConstant.USER_BASE));
        authorityEntities.add(findByName(AuthorityConstant.MEMBER_PAUSE));
        authorityEntities.add(findByName(AuthorityConstant.MEMBER_ACTIVE));
        return authorityEntities;
    }

    /**
     * Find suspended member authorities
     *
     * @return list of {@link AuthorityEntity}
     */
    @Override
    public List<AuthorityEntity> findMemberPause() {
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        authorityEntities.add(findByName(AuthorityConstant.USER_BASE));
        authorityEntities.add(findByName(AuthorityConstant.MEMBER_PAUSE));
        return authorityEntities;
    }

    /**
     * Find active hotel authorities
     *
     * @return list of {@link AuthorityEntity}
     */
    @Override
    public List<AuthorityEntity> findHotelActive() {
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        authorityEntities.add(findByName(AuthorityConstant.USER_BASE));
        authorityEntities.add(findByName(AuthorityConstant.HOTEL_PAUSE));
        authorityEntities.add(findByName(AuthorityConstant.HOTEL_ACTIVE));
        return authorityEntities;
    }

    /**
     * Find suspended hotel authority
     *
     * @return list of {@link AuthorityEntity}
     */
    @Override
    public List<AuthorityEntity> findHotelPause() {
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        authorityEntities.add(findByName(AuthorityConstant.USER_BASE));
        authorityEntities.add(findByName(AuthorityConstant.HOTEL_PAUSE));
        return authorityEntities;
    }

    /**
     * Find manager's authorities
     *
     * @return list of {@link AuthorityEntity}
     */
    @Override
    public List<AuthorityEntity> findManager() {
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        authorityEntities.add(findByName(AuthorityConstant.USER_BASE));
        authorityEntities.add(findByName(AuthorityConstant.MEMBER_ACTIVE));
        authorityEntities.add(findByName(AuthorityConstant.MEMBER_PAUSE));
        authorityEntities.add(findByName(AuthorityConstant.HOTEL_ACTIVE));
        authorityEntities.add(findByName(AuthorityConstant.HOTEL_PAUSE));
        authorityEntities.add(findByName(AuthorityConstant.MANAGER));
        return authorityEntities;
    }
}
