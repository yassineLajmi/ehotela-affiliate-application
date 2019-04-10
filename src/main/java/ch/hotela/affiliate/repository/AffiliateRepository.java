package ch.hotela.affiliate.repository;

import ch.hotela.affiliate.domain.Affiliate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Affiliate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffiliateRepository extends JpaRepository<Affiliate, Long> {

}
