package io.github.msufp.register.service;

import io.github.msufp.register.service.dto.ParticipationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Participation.
 */
public interface ParticipationService {

    /**
     * Save a participation.
     *
     * @param participationDTO the entity to save
     * @return the persisted entity
     */
    ParticipationDTO save(ParticipationDTO participationDTO);

    /**
     * Get all the participations.
     *
     * @return the list of entities
     */
    List<ParticipationDTO> findAll();


    /**
     * Get the "id" participation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParticipationDTO> findOne(Long id);

    /**
     * Delete the "id" participation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
