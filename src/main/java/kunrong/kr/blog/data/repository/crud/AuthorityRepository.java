package kunrong.kr.blog.data.repository.crud;

import kunrong.kr.blog.data.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity,Integer> {
    AuthorityEntity findByName(String name);

    List<AuthorityEntity> findByNameLike(String name);

    List<AuthorityEntity> findByNameStartingWith(String name);
}
