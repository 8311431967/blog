package kunrong.kr.blog.data.repository.page;

import kunrong.kr.blog.data.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlogPageResository extends PagingAndSortingRepository<UserEntity,Integer> {
}
