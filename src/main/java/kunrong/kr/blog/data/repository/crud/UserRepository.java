package kunrong.kr.blog.data.repository.crud;

import kunrong.kr.blog.data.entity.UserEntity;
import kunrong.kr.blog.util.enums.UserType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface UserRepository extends CrudRepository<UserEntity,Integer> {
    UserEntity findByName(String name);

    List<UserEntity> findByType(UserType type);
}
