package ch.hotela.affiliate.web.rest;

import ch.hotela.affiliate.AffiliateApp;

import ch.hotela.affiliate.domain.Affiliate;
import ch.hotela.affiliate.repository.AffiliateRepository;
import ch.hotela.affiliate.service.AffiliateService;
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
import java.math.BigDecimal;
import java.util.List;


import static ch.hotela.affiliate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AffiliateResource REST controller.
 *
 * @see AffiliateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AffiliateApp.class)
public class AffiliateResourceIntTest {

    private static final Long DEFAULT_NUMERO_AFFILIE = 1L;
    private static final Long UPDATED_NUMERO_AFFILIE = 2L;

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMS = "AAAAAAAAAA";
    private static final String UPDATED_NOMS = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_LIGNE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_LIGNE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_LIGNE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_LIGNE_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_POSTAL = 1;
    private static final Integer UPDATED_NUMERO_POSTAL = 2;

    private static final String DEFAULT_LOCALITE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALITE = "BBBBBBBBBB";

    private static final String DEFAULT_CANTON = "AAAAAAAAAA";
    private static final String UPDATED_CANTON = "BBBBBBBBBB";

    private static final Integer DEFAULT_LANGUE = 1;
    private static final Integer UPDATED_LANGUE = 2;

    private static final Integer DEFAULT_GROUPE_CODE = 1;
    private static final Integer UPDATED_GROUPE_CODE = 2;

    private static final String DEFAULT_GROUPE_NOM = "AAAAAAAAAA";
    private static final String UPDATED_GROUPE_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_CCNT = 1;
    private static final Integer UPDATED_IS_CCNT = 2;

    private static final String DEFAULT_TIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_WEEKLY_WORK_TIME = new BigDecimal(1);
    private static final BigDecimal UPDATED_WEEKLY_WORK_TIME = new BigDecimal(2);

    @Autowired
    private AffiliateRepository affiliateRepository;

    @Autowired
    private AffiliateService affiliateService;

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

    private MockMvc restAffiliateMockMvc;

    private Affiliate affiliate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AffiliateResource affiliateResource = new AffiliateResource(affiliateService);
        this.restAffiliateMockMvc = MockMvcBuilders.standaloneSetup(affiliateResource)
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
    public static Affiliate createEntity(EntityManager em) {
        Affiliate affiliate = new Affiliate()
            .numeroAffilie(DEFAULT_NUMERO_AFFILIE)
            .titre(DEFAULT_TITRE)
            .noms(DEFAULT_NOMS)
            .adresseLigne1(DEFAULT_ADRESSE_LIGNE_1)
            .adresseLigne2(DEFAULT_ADRESSE_LIGNE_2)
            .numeroPostal(DEFAULT_NUMERO_POSTAL)
            .localite(DEFAULT_LOCALITE)
            .canton(DEFAULT_CANTON)
            .langue(DEFAULT_LANGUE)
            .groupeCode(DEFAULT_GROUPE_CODE)
            .groupeNom(DEFAULT_GROUPE_NOM)
            .isCcnt(DEFAULT_IS_CCNT)
            .timeType(DEFAULT_TIME_TYPE)
            .groupType(DEFAULT_GROUP_TYPE)
            .weeklyWorkTime(DEFAULT_WEEKLY_WORK_TIME);
        return affiliate;
    }

    @Before
    public void initTest() {
        affiliate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffiliate() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // Create the Affiliate
        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isCreated());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate + 1);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getNumeroAffilie()).isEqualTo(DEFAULT_NUMERO_AFFILIE);
        assertThat(testAffiliate.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testAffiliate.getNoms()).isEqualTo(DEFAULT_NOMS);
        assertThat(testAffiliate.getAdresseLigne1()).isEqualTo(DEFAULT_ADRESSE_LIGNE_1);
        assertThat(testAffiliate.getAdresseLigne2()).isEqualTo(DEFAULT_ADRESSE_LIGNE_2);
        assertThat(testAffiliate.getNumeroPostal()).isEqualTo(DEFAULT_NUMERO_POSTAL);
        assertThat(testAffiliate.getLocalite()).isEqualTo(DEFAULT_LOCALITE);
        assertThat(testAffiliate.getCanton()).isEqualTo(DEFAULT_CANTON);
        assertThat(testAffiliate.getLangue()).isEqualTo(DEFAULT_LANGUE);
        assertThat(testAffiliate.getGroupeCode()).isEqualTo(DEFAULT_GROUPE_CODE);
        assertThat(testAffiliate.getGroupeNom()).isEqualTo(DEFAULT_GROUPE_NOM);
        assertThat(testAffiliate.getIsCcnt()).isEqualTo(DEFAULT_IS_CCNT);
        assertThat(testAffiliate.getTimeType()).isEqualTo(DEFAULT_TIME_TYPE);
        assertThat(testAffiliate.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
        assertThat(testAffiliate.getWeeklyWorkTime()).isEqualTo(DEFAULT_WEEKLY_WORK_TIME);
    }

    @Test
    @Transactional
    public void createAffiliateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // Create the Affiliate with an existing ID
        affiliate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroAffilieIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setNumeroAffilie(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setGroupType(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeeklyWorkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setWeeklyWorkTime(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAffiliates() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList
        restAffiliateMockMvc.perform(get("/api/affiliates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliate.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroAffilie").value(hasItem(DEFAULT_NUMERO_AFFILIE.intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].noms").value(hasItem(DEFAULT_NOMS.toString())))
            .andExpect(jsonPath("$.[*].adresseLigne1").value(hasItem(DEFAULT_ADRESSE_LIGNE_1.toString())))
            .andExpect(jsonPath("$.[*].adresseLigne2").value(hasItem(DEFAULT_ADRESSE_LIGNE_2.toString())))
            .andExpect(jsonPath("$.[*].numeroPostal").value(hasItem(DEFAULT_NUMERO_POSTAL)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE.toString())))
            .andExpect(jsonPath("$.[*].canton").value(hasItem(DEFAULT_CANTON.toString())))
            .andExpect(jsonPath("$.[*].langue").value(hasItem(DEFAULT_LANGUE)))
            .andExpect(jsonPath("$.[*].groupeCode").value(hasItem(DEFAULT_GROUPE_CODE)))
            .andExpect(jsonPath("$.[*].groupeNom").value(hasItem(DEFAULT_GROUPE_NOM.toString())))
            .andExpect(jsonPath("$.[*].isCcnt").value(hasItem(DEFAULT_IS_CCNT)))
            .andExpect(jsonPath("$.[*].timeType").value(hasItem(DEFAULT_TIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].weeklyWorkTime").value(hasItem(DEFAULT_WEEKLY_WORK_TIME.intValue())));
    }
    
    @Test
    @Transactional
    public void getAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get the affiliate
        restAffiliateMockMvc.perform(get("/api/affiliates/{id}", affiliate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(affiliate.getId().intValue()))
            .andExpect(jsonPath("$.numeroAffilie").value(DEFAULT_NUMERO_AFFILIE.intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.noms").value(DEFAULT_NOMS.toString()))
            .andExpect(jsonPath("$.adresseLigne1").value(DEFAULT_ADRESSE_LIGNE_1.toString()))
            .andExpect(jsonPath("$.adresseLigne2").value(DEFAULT_ADRESSE_LIGNE_2.toString()))
            .andExpect(jsonPath("$.numeroPostal").value(DEFAULT_NUMERO_POSTAL))
            .andExpect(jsonPath("$.localite").value(DEFAULT_LOCALITE.toString()))
            .andExpect(jsonPath("$.canton").value(DEFAULT_CANTON.toString()))
            .andExpect(jsonPath("$.langue").value(DEFAULT_LANGUE))
            .andExpect(jsonPath("$.groupeCode").value(DEFAULT_GROUPE_CODE))
            .andExpect(jsonPath("$.groupeNom").value(DEFAULT_GROUPE_NOM.toString()))
            .andExpect(jsonPath("$.isCcnt").value(DEFAULT_IS_CCNT))
            .andExpect(jsonPath("$.timeType").value(DEFAULT_TIME_TYPE.toString()))
            .andExpect(jsonPath("$.groupType").value(DEFAULT_GROUP_TYPE.toString()))
            .andExpect(jsonPath("$.weeklyWorkTime").value(DEFAULT_WEEKLY_WORK_TIME.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAffiliate() throws Exception {
        // Get the affiliate
        restAffiliateMockMvc.perform(get("/api/affiliates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffiliate() throws Exception {
        // Initialize the database
        affiliateService.save(affiliate);

        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Update the affiliate
        Affiliate updatedAffiliate = affiliateRepository.findById(affiliate.getId()).get();
        // Disconnect from session so that the updates on updatedAffiliate are not directly saved in db
        em.detach(updatedAffiliate);
        updatedAffiliate
            .numeroAffilie(UPDATED_NUMERO_AFFILIE)
            .titre(UPDATED_TITRE)
            .noms(UPDATED_NOMS)
            .adresseLigne1(UPDATED_ADRESSE_LIGNE_1)
            .adresseLigne2(UPDATED_ADRESSE_LIGNE_2)
            .numeroPostal(UPDATED_NUMERO_POSTAL)
            .localite(UPDATED_LOCALITE)
            .canton(UPDATED_CANTON)
            .langue(UPDATED_LANGUE)
            .groupeCode(UPDATED_GROUPE_CODE)
            .groupeNom(UPDATED_GROUPE_NOM)
            .isCcnt(UPDATED_IS_CCNT)
            .timeType(UPDATED_TIME_TYPE)
            .groupType(UPDATED_GROUP_TYPE)
            .weeklyWorkTime(UPDATED_WEEKLY_WORK_TIME);

        restAffiliateMockMvc.perform(put("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAffiliate)))
            .andExpect(status().isOk());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getNumeroAffilie()).isEqualTo(UPDATED_NUMERO_AFFILIE);
        assertThat(testAffiliate.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testAffiliate.getNoms()).isEqualTo(UPDATED_NOMS);
        assertThat(testAffiliate.getAdresseLigne1()).isEqualTo(UPDATED_ADRESSE_LIGNE_1);
        assertThat(testAffiliate.getAdresseLigne2()).isEqualTo(UPDATED_ADRESSE_LIGNE_2);
        assertThat(testAffiliate.getNumeroPostal()).isEqualTo(UPDATED_NUMERO_POSTAL);
        assertThat(testAffiliate.getLocalite()).isEqualTo(UPDATED_LOCALITE);
        assertThat(testAffiliate.getCanton()).isEqualTo(UPDATED_CANTON);
        assertThat(testAffiliate.getLangue()).isEqualTo(UPDATED_LANGUE);
        assertThat(testAffiliate.getGroupeCode()).isEqualTo(UPDATED_GROUPE_CODE);
        assertThat(testAffiliate.getGroupeNom()).isEqualTo(UPDATED_GROUPE_NOM);
        assertThat(testAffiliate.getIsCcnt()).isEqualTo(UPDATED_IS_CCNT);
        assertThat(testAffiliate.getTimeType()).isEqualTo(UPDATED_TIME_TYPE);
        assertThat(testAffiliate.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
        assertThat(testAffiliate.getWeeklyWorkTime()).isEqualTo(UPDATED_WEEKLY_WORK_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Create the Affiliate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliateMockMvc.perform(put("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAffiliate() throws Exception {
        // Initialize the database
        affiliateService.save(affiliate);

        int databaseSizeBeforeDelete = affiliateRepository.findAll().size();

        // Delete the affiliate
        restAffiliateMockMvc.perform(delete("/api/affiliates/{id}", affiliate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affiliate.class);
        Affiliate affiliate1 = new Affiliate();
        affiliate1.setId(1L);
        Affiliate affiliate2 = new Affiliate();
        affiliate2.setId(affiliate1.getId());
        assertThat(affiliate1).isEqualTo(affiliate2);
        affiliate2.setId(2L);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
        affiliate1.setId(null);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
    }
}
