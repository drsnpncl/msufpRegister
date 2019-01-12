package io.github.msufp.register.service.impl;

import io.github.msufp.register.service.ParticipationService;
import io.github.msufp.register.domain.Participation;
import io.github.msufp.register.repository.ParticipationRepository;
import io.github.msufp.register.service.dto.ParticipationDTO;
import io.github.msufp.register.service.mapper.ParticipationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Participation.
 */
@Service
@Transactional
public class ParticipationServiceImpl implements ParticipationService {

    private final Logger log = LoggerFactory.getLogger(ParticipationServiceImpl.class);

    private final ParticipationRepository participationRepository;

    private final ParticipationMapper participationMapper;

    public ParticipationServiceImpl(ParticipationRepository participationRepository, ParticipationMapper participationMapper) {
        this.participationRepository = participationRepository;
        this.participationMapper = participationMapper;
    }

    /**
     * Save a participation.
     *
     * @param participationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParticipationDTO save(ParticipationDTO participationDTO) {
        log.debug("Request to save Participation : {}", participationDTO);

        Participation participation = participationMapper.toEntity(participationDTO);
        participation = participationRepository.save(participation);
        return participationMapper.toDto(participation);
    }

    /**
     * Get all the participations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParticipationDTO> findAll() {
        log.debug("Request to get all Participations");
        return participationRepository.findAll().stream()
            .map(participationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one participation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipationDTO> findOne(Long id) {
        log.debug("Request to get Participation : {}", id);
        return participationRepository.findById(id)
            .map(participationMapper::toDto);
    }

    /**
     * Delete the participation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participation : {}", id);
        participationRepository.deleteById(id);
    }
}
