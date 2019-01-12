package io.github.msufp.register.service.mapper;

import io.github.msufp.register.domain.*;
import io.github.msufp.register.service.dto.UserGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserGroup and its DTO UserGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserGroupMapper extends EntityMapper<UserGroupDTO, UserGroup> {



    default UserGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        return userGroup;
    }
}
