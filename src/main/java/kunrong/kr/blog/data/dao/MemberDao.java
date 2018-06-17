package kunrong.kr.blog.data.dao;

import kunrong.kr.blog.data.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MemberDao {

    /**
     * Find member by id
     * @param id member id
     * @return {@link MemberEntity}
     */
    MemberEntity findById(int id);

    /**
     * Find member by name
     * @param name member name
     * @return {@link MemberEntity}
     */
    MemberEntity findByName(String name);

    /**
     * Update or create member entity
     * @param memberEntity member entity
     * @return {@link MemberEntity}
     */
    MemberEntity save(MemberEntity memberEntity);

    /**
     * Soft delete
     * @param id member id
     */
    void delete(int id);

    /**
     * Find all members
     * @return list of {@link MemberEntity}
     */
    List<MemberEntity> findAll();

    Page<MemberEntity> findAll(int page, int pageSize, String sortColumn, Sort.Direction sortDirection);

}
