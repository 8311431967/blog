package kunrong.kr.blog.data.repository.crud;

import kunrong.kr.blog.data.entity.UserEntity;
import kunrong.kr.blog.util.enums.UserType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<UserEntity,Integer> {
    UserEntity findByName(String name);

    List<UserEntity> findByType(UserType type);
}
