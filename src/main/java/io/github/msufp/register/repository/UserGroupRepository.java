package io.github.msufp.register.repository;

import io.github.msufp.register.domain.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    @Query(value = "select distinct user_group from UserGroup user_group left join fetch user_group.users",
        countQuery = "select count(distinct user_group) from UserGroup user_group")
    Page<UserGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct user_group from UserGroup user_group left join fetch user_group.users")
    List<UserGroup> findAllWithEagerRelationships();

    @Query("select user_group from UserGroup user_group left join fetch user_group.users where user_group.id =:id")
    Optional<UserGroup> findOneWithEagerRelationships(@Param("id") Long id);

}
