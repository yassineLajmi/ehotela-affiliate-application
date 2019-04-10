package ch.hotela.affiliate.web.rest;
import ch.hotela.affiliate.domain.AffSubscription;
import ch.hotela.affiliate.repository.AffSubscriptionRepository;
import ch.hotela.affiliate.web.rest.errors.BadRequestAlertException;
import ch.hotela.affiliate.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AffSubscription.
 */
@RestController
@RequestMapping("/api")
public class AffSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(AffSubscriptionResource.class);

    private static final String ENTITY_NAME = "affSubscription";

    private final AffSubscriptionRepository affSubscriptionRepository;

    public AffSubscriptionResource(AffSubscriptionRepository affSubscriptionRepository) {
        this.affSubscriptionRepository = affSubscriptionRepository;
    }

    /**
     * POST  /aff-subscriptions : Create a new affSubscription.
     *
     * @param affSubscription the affSubscription to create
     * @return the ResponseEntity with status 201 (Created) and with body the new affSubscription, or with status 400 (Bad Request) if the affSubscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aff-subscriptions")
    public ResponseEntity<AffSubscription> createAffSubscription(@Valid @RequestBody AffSubscription affSubscription) throws URISyntaxException {
        log.debug("REST request to save AffSubscription : {}", affSubscription);
        if (affSubscription.getId() != null) {
            throw new BadRequestAlertException("A new affSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AffSubscription result = affSubscriptionRepository.save(affSubscription);
        return ResponseEntity.created(new URI("/api/aff-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aff-subscriptions : Updates an existing affSubscription.
     *
     * @param affSubscription the affSubscription to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated affSubscription,
     * or with status 400 (Bad Request) if the affSubscription is not valid,
     * or with status 500 (Internal Server Error) if the affSubscription couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aff-subscriptions")
    public ResponseEntity<AffSubscription> updateAffSubscription(@Valid @RequestBody AffSubscription affSubscription) throws URISyntaxException {
        log.debug("REST request to update AffSubscription : {}", affSubscription);
        if (affSubscription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AffSubscription result = affSubscriptionRepository.save(affSubscription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, affSubscription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aff-subscriptions : get all the affSubscriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of affSubscriptions in body
     */
    @GetMapping("/aff-subscriptions")
    public List<AffSubscription> getAllAffSubscriptions() {
        log.debug("REST request to get all AffSubscriptions");
        return affSubscriptionRepository.findAll();
    }

    /**
     * GET  /aff-subscriptions/:id : get the "id" affSubscription.
     *
     * @param id the id of the affSubscription to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the affSubscription, or with status 404 (Not Found)
     */
    @GetMapping("/aff-subscriptions/{id}")
    public ResponseEntity<AffSubscription> getAffSubscription(@PathVariable Long id) {
        log.debug("REST request to get AffSubscription : {}", id);
        Optional<AffSubscription> affSubscription = affSubscriptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(affSubscription);
    }

    /**
     * DELETE  /aff-subscriptions/:id : delete the "id" affSubscription.
     *
     * @param id the id of the affSubscription to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aff-subscriptions/{id}")
    public ResponseEntity<Void> deleteAffSubscription(@PathVariable Long id) {
        log.debug("REST request to delete AffSubscription : {}", id);
        affSubscriptionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
