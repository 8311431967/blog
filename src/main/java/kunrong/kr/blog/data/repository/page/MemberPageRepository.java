package kunrong.kr.blog.data.repository.page;

import kunrong.kr.blog.data.entity.MemberEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberPageRepository extends PagingAndSortingRepository<MemberEntity,Integer> {
}
