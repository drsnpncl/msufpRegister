package io.github.msufp.register.service.mapper;

import io.github.msufp.register.domain.*;
import io.github.msufp.register.service.dto.ParticipationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Participation and its DTO ParticipationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserGroupMapper.class, EventMapper.class})
public interface ParticipationMapper extends EntityMapper<ParticipationDTO, Participation> {

    @Mapping(source = "userGroup.id", target = "userGroupId")
    @Mapping(source = "event.id", target = "eventId")
    ParticipationDTO toDto(Participation participation);

    @Mapping(source = "userGroupId", target = "userGroup")
    @Mapping(source = "eventId", target = "event")
    Participation toEntity(ParticipationDTO participationDTO);

    default Participation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Participation participation = new Participation();
        participation.setId(id);
        return participation;
    }
}
