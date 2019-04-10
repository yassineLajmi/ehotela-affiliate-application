package ch.hotela.affiliate.repository;

import ch.hotela.affiliate.domain.AffSubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AffSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffSubscriptionRepository extends JpaRepository<AffSubscription, Long> {

}
