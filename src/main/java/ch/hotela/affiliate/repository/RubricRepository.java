package ch.hotela.affiliate.repository;

import ch.hotela.affiliate.domain.Rubric;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rubric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RubricRepository extends JpaRepository<Rubric, Long> {

}
