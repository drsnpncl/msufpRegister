package io.github.msufp.register.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.msufp.register.service.ParticipationService;
import io.github.msufp.register.web.rest.errors.BadRequestAlertException;
import io.github.msufp.register.web.rest.util.HeaderUtil;
import io.github.msufp.register.service.dto.ParticipationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Participation.
 */
@RestController
@RequestMapping("/api")
public class ParticipationResource {

    private final Logger log = LoggerFactory.getLogger(ParticipationResource.class);

    private static final String ENTITY_NAME = "participation";

    private final ParticipationService participationService;

    public ParticipationResource(ParticipationService participationService) {
        this.participationService = participationService;
    }

    /**
     * POST  /participations : Create a new participation.
     *
     * @param participationDTO the participationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new participationDTO, or with status 400 (Bad Request) if the participation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/participations")
    @Timed
    public ResponseEntity<ParticipationDTO> createParticipation(@RequestBody ParticipationDTO participationDTO) throws URISyntaxException {
        log.debug("REST request to save Participation : {}", participationDTO);
        if (participationDTO.getId() != null) {
            throw new BadRequestAlertException("A new participation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParticipationDTO result = participationService.save(participationDTO);
        return ResponseEntity.created(new URI("/api/participations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /participations : Updates an existing participation.
     *
     * @param participationDTO the participationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated participationDTO,
     * or with status 400 (Bad Request) if the participationDTO is not valid,
     * or with status 500 (Internal Server Error) if the participationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/participations")
    @Timed
    public ResponseEntity<ParticipationDTO> updateParticipation(@RequestBody ParticipationDTO participationDTO) throws URISyntaxException {
        log.debug("REST request to update Participation : {}", participationDTO);
        if (participationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParticipationDTO result = participationService.save(participationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, participationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /participations : get all the participations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of participations in body
     */
    @GetMapping("/participations")
    @Timed
    public List<ParticipationDTO> getAllParticipations() {
        log.debug("REST request to get all Participations");
        return participationService.findAll();
    }

    /**
     * GET  /participations/:id : get the "id" participation.
     *
     * @param id the id of the participationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the participationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/participations/{id}")
    @Timed
    public ResponseEntity<ParticipationDTO> getParticipation(@PathVariable Long id) {
        log.debug("REST request to get Participation : {}", id);
        Optional<ParticipationDTO> participationDTO = participationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participationDTO);
    }

    /**
     * DELETE  /participations/:id : delete the "id" participation.
     *
     * @param id the id of the participationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/participations/{id}")
    @Timed
    public ResponseEntity<Void> deleteParticipation(@PathVariable Long id) {
        log.debug("REST request to delete Participation : {}", id);
        participationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
