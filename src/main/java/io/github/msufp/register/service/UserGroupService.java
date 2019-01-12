package io.github.msufp.register.service;

import io.github.msufp.register.service.dto.UserGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserGroup.
 */
public interface UserGroupService {

    /**
     * Save a userGroup.
     *
     * @param userGroupDTO the entity to save
     * @return the persisted entity
     */
    UserGroupDTO save(UserGroupDTO userGroupDTO);

    /**
     * Get all the userGroups.
     *
     * @return the list of entities
     */
    List<UserGroupDTO> findAll();

    /**
     * Get all the UserGroup with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<UserGroupDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" userGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserGroupDTO> findOne(Long id);

    /**
     * Delete the "id" userGroup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
