package ch.hotela.affiliate.web.rest;

import ch.hotela.affiliate.AffiliateApp;

import ch.hotela.affiliate.domain.Rubric;
import ch.hotela.affiliate.repository.RubricRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static ch.hotela.affiliate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RubricResource REST controller.
 *
 * @see RubricResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AffiliateApp.class)
public class RubricResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RUBRIC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RUBRIC_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_RATE = new BigDecimal(2);

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_AGE = 1;
    private static final Integer UPDATED_START_AGE = 2;

    private static final Integer DEFAULT_END_AGE = 1;
    private static final Integer UPDATED_END_AGE = 2;

    private static final BigDecimal DEFAULT_EMPLOYEE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EMPLOYEE_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DI_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DI_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AIL_SALARY_LIMIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AIL_SALARY_LIMIT = new BigDecimal(2);

    @Autowired
    private RubricRepository rubricRepository;

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

    private MockMvc restRubricMockMvc;

    private Rubric rubric;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RubricResource rubricResource = new RubricResource(rubricRepository);
        this.restRubricMockMvc = MockMvcBuilders.standaloneSetup(rubricResource)
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
    public static Rubric createEntity(EntityManager em) {
        Rubric rubric = new Rubric()
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .rubricType(DEFAULT_RUBRIC_TYPE)
            .totalRate(DEFAULT_TOTAL_RATE)
            .sex(DEFAULT_SEX)
            .startAge(DEFAULT_START_AGE)
            .endAge(DEFAULT_END_AGE)
            .employeeRate(DEFAULT_EMPLOYEE_RATE)
            .diRate(DEFAULT_DI_RATE)
            .ailSalaryLimit(DEFAULT_AIL_SALARY_LIMIT);
        return rubric;
    }

    @Before
    public void initTest() {
        rubric = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubric() throws Exception {
        int databaseSizeBeforeCreate = rubricRepository.findAll().size();

        // Create the Rubric
        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isCreated());

        // Validate the Rubric in the database
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeCreate + 1);
        Rubric testRubric = rubricList.get(rubricList.size() - 1);
        assertThat(testRubric.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRubric.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRubric.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRubric.getRubricType()).isEqualTo(DEFAULT_RUBRIC_TYPE);
        assertThat(testRubric.getTotalRate()).isEqualTo(DEFAULT_TOTAL_RATE);
        assertThat(testRubric.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testRubric.getStartAge()).isEqualTo(DEFAULT_START_AGE);
        assertThat(testRubric.getEndAge()).isEqualTo(DEFAULT_END_AGE);
        assertThat(testRubric.getEmployeeRate()).isEqualTo(DEFAULT_EMPLOYEE_RATE);
        assertThat(testRubric.getDiRate()).isEqualTo(DEFAULT_DI_RATE);
        assertThat(testRubric.getAilSalaryLimit()).isEqualTo(DEFAULT_AIL_SALARY_LIMIT);
    }

    @Test
    @Transactional
    public void createRubricWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rubricRepository.findAll().size();

        // Create the Rubric with an existing ID
        rubric.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        // Validate the Rubric in the database
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricRepository.findAll().size();
        // set the field null
        rubric.setDescription(null);

        // Create the Rubric, which fails.

        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricRepository.findAll().size();
        // set the field null
        rubric.setStartDate(null);

        // Create the Rubric, which fails.

        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricRepository.findAll().size();
        // set the field null
        rubric.setEndDate(null);

        // Create the Rubric, which fails.

        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRubricTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricRepository.findAll().size();
        // set the field null
        rubric.setRubricType(null);

        // Create the Rubric, which fails.

        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricRepository.findAll().size();
        // set the field null
        rubric.setStartAge(null);

        // Create the Rubric, which fails.

        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricRepository.findAll().size();
        // set the field null
        rubric.setEndAge(null);

        // Create the Rubric, which fails.

        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmployeeRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubricRepository.findAll().size();
        // set the field null
        rubric.setEmployeeRate(null);

        // Create the Rubric, which fails.

        restRubricMockMvc.perform(post("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRubrics() throws Exception {
        // Initialize the database
        rubricRepository.saveAndFlush(rubric);

        // Get all the rubricList
        restRubricMockMvc.perform(get("/api/rubrics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubric.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].rubricType").value(hasItem(DEFAULT_RUBRIC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totalRate").value(hasItem(DEFAULT_TOTAL_RATE.intValue())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].startAge").value(hasItem(DEFAULT_START_AGE)))
            .andExpect(jsonPath("$.[*].endAge").value(hasItem(DEFAULT_END_AGE)))
            .andExpect(jsonPath("$.[*].employeeRate").value(hasItem(DEFAULT_EMPLOYEE_RATE.intValue())))
            .andExpect(jsonPath("$.[*].diRate").value(hasItem(DEFAULT_DI_RATE.intValue())))
            .andExpect(jsonPath("$.[*].ailSalaryLimit").value(hasItem(DEFAULT_AIL_SALARY_LIMIT.intValue())));
    }
    
    @Test
    @Transactional
    public void getRubric() throws Exception {
        // Initialize the database
        rubricRepository.saveAndFlush(rubric);

        // Get the rubric
        restRubricMockMvc.perform(get("/api/rubrics/{id}", rubric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rubric.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.rubricType").value(DEFAULT_RUBRIC_TYPE.toString()))
            .andExpect(jsonPath("$.totalRate").value(DEFAULT_TOTAL_RATE.intValue()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.startAge").value(DEFAULT_START_AGE))
            .andExpect(jsonPath("$.endAge").value(DEFAULT_END_AGE))
            .andExpect(jsonPath("$.employeeRate").value(DEFAULT_EMPLOYEE_RATE.intValue()))
            .andExpect(jsonPath("$.diRate").value(DEFAULT_DI_RATE.intValue()))
            .andExpect(jsonPath("$.ailSalaryLimit").value(DEFAULT_AIL_SALARY_LIMIT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRubric() throws Exception {
        // Get the rubric
        restRubricMockMvc.perform(get("/api/rubrics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubric() throws Exception {
        // Initialize the database
        rubricRepository.saveAndFlush(rubric);

        int databaseSizeBeforeUpdate = rubricRepository.findAll().size();

        // Update the rubric
        Rubric updatedRubric = rubricRepository.findById(rubric.getId()).get();
        // Disconnect from session so that the updates on updatedRubric are not directly saved in db
        em.detach(updatedRubric);
        updatedRubric
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .rubricType(UPDATED_RUBRIC_TYPE)
            .totalRate(UPDATED_TOTAL_RATE)
            .sex(UPDATED_SEX)
            .startAge(UPDATED_START_AGE)
            .endAge(UPDATED_END_AGE)
            .employeeRate(UPDATED_EMPLOYEE_RATE)
            .diRate(UPDATED_DI_RATE)
            .ailSalaryLimit(UPDATED_AIL_SALARY_LIMIT);

        restRubricMockMvc.perform(put("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRubric)))
            .andExpect(status().isOk());

        // Validate the Rubric in the database
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeUpdate);
        Rubric testRubric = rubricList.get(rubricList.size() - 1);
        assertThat(testRubric.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRubric.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRubric.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRubric.getRubricType()).isEqualTo(UPDATED_RUBRIC_TYPE);
        assertThat(testRubric.getTotalRate()).isEqualTo(UPDATED_TOTAL_RATE);
        assertThat(testRubric.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testRubric.getStartAge()).isEqualTo(UPDATED_START_AGE);
        assertThat(testRubric.getEndAge()).isEqualTo(UPDATED_END_AGE);
        assertThat(testRubric.getEmployeeRate()).isEqualTo(UPDATED_EMPLOYEE_RATE);
        assertThat(testRubric.getDiRate()).isEqualTo(UPDATED_DI_RATE);
        assertThat(testRubric.getAilSalaryLimit()).isEqualTo(UPDATED_AIL_SALARY_LIMIT);
    }

    @Test
    @Transactional
    public void updateNonExistingRubric() throws Exception {
        int databaseSizeBeforeUpdate = rubricRepository.findAll().size();

        // Create the Rubric

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRubricMockMvc.perform(put("/api/rubrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubric)))
            .andExpect(status().isBadRequest());

        // Validate the Rubric in the database
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRubric() throws Exception {
        // Initialize the database
        rubricRepository.saveAndFlush(rubric);

        int databaseSizeBeforeDelete = rubricRepository.findAll().size();

        // Delete the rubric
        restRubricMockMvc.perform(delete("/api/rubrics/{id}", rubric.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rubric> rubricList = rubricRepository.findAll();
        assertThat(rubricList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rubric.class);
        Rubric rubric1 = new Rubric();
        rubric1.setId(1L);
        Rubric rubric2 = new Rubric();
        rubric2.setId(rubric1.getId());
        assertThat(rubric1).isEqualTo(rubric2);
        rubric2.setId(2L);
        assertThat(rubric1).isNotEqualTo(rubric2);
        rubric1.setId(null);
        assertThat(rubric1).isNotEqualTo(rubric2);
    }
}
