package ch.hotela.affiliate.web.rest;

import ch.hotela.affiliate.AffiliateApp;

import ch.hotela.affiliate.domain.AffSubscription;
import ch.hotela.affiliate.repository.AffSubscriptionRepository;
import ch.hotela.affiliate.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static ch.hotela.affiliate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AffSubscriptionResource REST controller.
 *
 * @see AffSubscriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AffiliateApp.class)
public class AffSubscriptionResourceIntTest {

    private static final String DEFAULT_ID_ASSURANCE = "AAAAAAAAAA";
    private static final String UPDATED_ID_ASSURANCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AffSubscriptionRepository affSubscriptionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAffSubscriptionMockMvc;

    private AffSubscription affSubscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AffSubscriptionResource affSubscriptionResource = new AffSubscriptionResource(affSubscriptionRepository);
        this.restAffSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(affSubscriptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffSubscription createEntity(EntityManager em) {
        AffSubscription affSubscription = new AffSubscription()
            .idAssurance(DEFAULT_ID_ASSURANCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return affSubscription;
    }

    @Before
    public void initTest() {
        affSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffSubscription() throws Exception {
        int databaseSizeBeforeCreate = affSubscriptionRepository.findAll().size();

        // Create the AffSubscription
        restAffSubscriptionMockMvc.perform(post("/api/aff-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affSubscription)))
            .andExpect(status().isCreated());

        // Validate the AffSubscription in the database
        List<AffSubscription> affSubscriptionList = affSubscriptionRepository.findAll();
        assertThat(affSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        AffSubscription testAffSubscription = affSubscriptionList.get(affSubscriptionList.size() - 1);
        assertThat(testAffSubscription.getIdAssurance()).isEqualTo(DEFAULT_ID_ASSURANCE);
        assertThat(testAffSubscription.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAffSubscription.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createAffSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affSubscriptionRepository.findAll().size();

        // Create the AffSubscription with an existing ID
        affSubscription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffSubscriptionMockMvc.perform(post("/api/aff-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affSubscription)))
            .andExpect(status().isBadRequest());

        // Validate the AffSubscription in the database
        List<AffSubscription> affSubscriptionList = affSubscriptionRepository.findAll();
        assertThat(affSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdAssuranceIsRequired() throws Exception {
        int databaseSizeBeforeTest = affSubscriptionRepository.findAll().size();
        // set the field null
        affSubscription.setIdAssurance(null);

        // Create the AffSubscription, which fails.

        restAffSubscriptionMockMvc.perform(post("/api/aff-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affSubscription)))
            .andExpect(status().isBadRequest());

        List<AffSubscription> affSubscriptionList = affSubscriptionRepository.findAll();
        assertThat(affSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = affSubscriptionRepository.findAll().size();
        // set the field null
        affSubscription.setStartDate(null);

        // Create the AffSubscription, which fails.

        restAffSubscriptionMockMvc.perform(post("/api/aff-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affSubscription)))
            .andExpect(status().isBadRequest());

        List<AffSubscription> affSubscriptionList = affSubscriptionRepository.findAll();
        assertThat(affSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = affSubscriptionRepository.findAll().size();
        // set the field null
        affSubscription.setEndDate(null);

        // Create the AffSubscription, which fails.

        restAffSubscriptionMockMvc.perform(post("/api/aff-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affSubscription)))
            .andExpect(status().isBadRequest());

        List<AffSubscription> affSubscriptionList = affSubscriptionRepository.findAll();
        assertThat(affSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAffSubscriptions() throws Exception {
        // Initialize the database
        affSubscriptionRepository.saveAndFlush(affSubscription);

        // Get all the affSubscriptionList
        restAffSubscriptionMockMvc.perform(get("/api/aff-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAssurance").value(hasItem(DEFAULT_ID_ASSURANCE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAffSubscription() throws Exception {
        // Initialize the database
        affSubscriptionRepository.saveAndFlush(affSubscription);

        // Get the affSubscription
        restAffSubscriptionMockMvc.perform(get("/api/aff-subscriptions/{id}", affSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(affSubscription.getId().intValue()))
            .andExpect(jsonPath("$.idAssurance").value(DEFAULT_ID_ASSURANCE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAffSubscription() throws Exception {
        // Get the affSubscription
        restAffSubscriptionMockMvc.perform(get("/api/aff-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffSubscription() throws Exception {
        // Initialize the database
        affSubscriptionRepository.saveAndFlush(affSubscription);

        int databaseSizeBeforeUpdate = affSubscriptionRepository.findAll().size();

        // Update the affSubscription
        AffSubscription updatedAffSubscription = affSubscriptionRepository.findById(affSubscription.getId()).get();
        // Disconnect from session so that the updates on updatedAffSubscription are not directly saved in db
        em.detach(updatedAffSubscription);
        updatedAffSubscription
            .idAssurance(UPDATED_ID_ASSURANCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restAffSubscriptionMockMvc.perform(put("/api/aff-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAffSubscription)))
            .andExpect(status().isOk());

        // Validate the AffSubscription in the database
        List<AffSubscription> affSubscriptionList = affSubscriptionRepository.findAll();
        assertThat(affSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        AffSubscription testAffSubscription = affSubscriptionList.get(affSubscriptionList.size() - 1);
        assertThat(testAffSubscription.getIdAssurance()).isEqualTo(UPDATED_ID_ASSURANCE);
        assertThat(testAffSubscription.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAffSubscription.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAffSubscription() throws Exception {
        int databaseSizeBeforeUpdate = affSubscriptionRepository.findAll().size();

        // Create the AffSubscription

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffSubscriptionMockMvc.perform(put("/api/aff-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affSubscription)))
            .andExpect(status().isBadRequest());

        // Validate the AffSubscription in the database
        List<AffSubscription> affSubscriptionList = affSubscriptionRepository.findAll();
        assertThat(affSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAffSubscription() throws Exception {
        // Initialize the database
        affSubscriptionRepository.saveAndFlush(affSubscription);

        int databaseSizeBeforeDelete = affSubscriptionRepository.findAll().size();

        // Delete the affSubscription
        restAffSubscriptionMockMvc.perform(delete("/api/aff-subscriptions/{id}", affSubscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AffSubscription> affSubscriptionList = affSubscriptionRepository.findAll();
        assertThat(affSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffSubscription.class);
        AffSubscription affSubscription1 = new AffSubscription();
        affSubscription1.setId(1L);
        AffSubscription affSubscription2 = new AffSubscription();
        affSubscription2.setId(affSubscription1.getId());
        assertThat(affSubscription1).isEqualTo(affSubscription2);
        affSubscription2.setId(2L);
        assertThat(affSubscription1).isNotEqualTo(affSubscription2);
        affSubscription1.setId(null);
        assertThat(affSubscription1).isNotEqualTo(affSubscription2);
    }
}
