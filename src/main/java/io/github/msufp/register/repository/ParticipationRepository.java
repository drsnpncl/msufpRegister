package io.github.msufp.register.repository;

import io.github.msufp.register.domain.Participation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Participation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {

}
