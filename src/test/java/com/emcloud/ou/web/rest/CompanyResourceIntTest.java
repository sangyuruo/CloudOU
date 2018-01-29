package com.emcloud.ou.web.rest;

import com.emcloud.ou.EmCloudOuApp;

import com.emcloud.ou.config.SecurityBeanOverrideConfiguration;

import com.emcloud.ou.domain.Company;
import com.emcloud.ou.repository.CompanyRepository;
import com.emcloud.ou.service.CompanyService;
import com.emcloud.ou.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.emcloud.ou.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanyResource REST controller.
 *
 * @see CompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudOuApp.class, SecurityBeanOverrideConfiguration.class})
public class CompanyResourceIntTest {

    private static final String DEFAULT_COMPANY_LONG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_LONG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_COMPANY_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL_ID = 1;
    private static final Integer UPDATED_LEVEL_ID = 2;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATTACHS_NUM = 1;
    private static final Integer UPDATED_ATTACHS_NUM = 2;

    private static final Integer DEFAULT_SEQ_NO = 1;
    private static final Integer UPDATED_SEQ_NO = 2;

    private static final Boolean DEFAULT_ENABLE = true;
    private static final Boolean UPDATED_ENABLE = false;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyMockMvc;

    private Company company;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyResource companyResource = new CompanyResource(companyService);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .cityName(DEFAULT_CITY_NAME)
            .companyLongName(DEFAULT_COMPANY_LONG_NAME)
            .companyName(DEFAULT_COMPANY_NAME)
            .parentCompanyName(DEFAULT_PARENT_COMPANY_NAME)
            .companyCode(DEFAULT_COMPANY_CODE)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .cityCode(DEFAULT_CITY_CODE)
            .addressCode(DEFAULT_ADDRESS_CODE)
            .addressName(DEFAULT_ADDRESS_NAME)
            .telephone(DEFAULT_TELEPHONE)
            .legalPerson(DEFAULT_LEGAL_PERSON)
            .parentCompanyCode(DEFAULT_PARENT_COMPANY_CODE)
            .levelId(DEFAULT_LEVEL_ID)
            .remark(DEFAULT_REMARK)
            .attachsNum(DEFAULT_ATTACHS_NUM)
            .seqNo(DEFAULT_SEQ_NO)
            .enable(DEFAULT_ENABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return company;
    }

    @Before
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyLongName()).isEqualTo(DEFAULT_COMPANY_LONG_NAME);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getParentCompanyName()).isEqualTo(DEFAULT_PARENT_COMPANY_NAME);
        assertThat(testCompany.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompany.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCompany.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testCompany.getAddressCode()).isEqualTo(DEFAULT_ADDRESS_CODE);
        assertThat(testCompany.getAddressName()).isEqualTo(DEFAULT_ADDRESS_NAME);
        assertThat(testCompany.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCompany.getLegalPerson()).isEqualTo(DEFAULT_LEGAL_PERSON);
        assertThat(testCompany.getParentCompanyCode()).isEqualTo(DEFAULT_PARENT_COMPANY_CODE);
        assertThat(testCompany.getLevelId()).isEqualTo(DEFAULT_LEVEL_ID);
        assertThat(testCompany.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testCompany.getAttachsNum()).isEqualTo(DEFAULT_ATTACHS_NUM);
        assertThat(testCompany.getSeqNo()).isEqualTo(DEFAULT_SEQ_NO);
        assertThat(testCompany.getEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testCompany.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCompany.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testCompany.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCompany.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCompanyName(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParentCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setParentCompanyName(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCompanyCode(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCountryCode(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCityCode(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setAddressName(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setEnable(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCreatedBy(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCreateTime(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setUpdatedBy(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setUpdateTime(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?seq_no=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyLongName").value(hasItem(DEFAULT_COMPANY_LONG_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentCompanyName").value(hasItem(DEFAULT_PARENT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].addressCode").value(hasItem(DEFAULT_ADDRESS_CODE.toString())))
            .andExpect(jsonPath("$.[*].addressName").value(hasItem(DEFAULT_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].legalPerson").value(hasItem(DEFAULT_LEGAL_PERSON.toString())))
            .andExpect(jsonPath("$.[*].parentCompanyCode").value(hasItem(DEFAULT_PARENT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].levelId").value(hasItem(DEFAULT_LEVEL_ID)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].attachsNum").value(hasItem(DEFAULT_ATTACHS_NUM)))
            .andExpect(jsonPath("$.[*].seqNo").value(hasItem(DEFAULT_SEQ_NO)))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyLongName").value(DEFAULT_COMPANY_LONG_NAME.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.parentCompanyName").value(DEFAULT_PARENT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE.toString()))
            .andExpect(jsonPath("$.addressCode").value(DEFAULT_ADDRESS_CODE.toString()))
            .andExpect(jsonPath("$.addressName").value(DEFAULT_ADDRESS_NAME.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.legalPerson").value(DEFAULT_LEGAL_PERSON.toString()))
            .andExpect(jsonPath("$.parentCompanyCode").value(DEFAULT_PARENT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.levelId").value(DEFAULT_LEVEL_ID))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.attachsNum").value(DEFAULT_ATTACHS_NUM))
            .andExpect(jsonPath("$.seqNo").value(DEFAULT_SEQ_NO))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyService.save(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findOne(company.getId());
        updatedCompany
            .companyLongName(UPDATED_COMPANY_LONG_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .parentCompanyName(UPDATED_PARENT_COMPANY_NAME)
            .companyCode(UPDATED_COMPANY_CODE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .cityCode(UPDATED_CITY_CODE)
            .addressCode(UPDATED_ADDRESS_CODE)
            .addressName(UPDATED_ADDRESS_NAME)
            .telephone(UPDATED_TELEPHONE)
            .legalPerson(UPDATED_LEGAL_PERSON)
            .parentCompanyCode(UPDATED_PARENT_COMPANY_CODE)
            .levelId(UPDATED_LEVEL_ID)
            .remark(UPDATED_REMARK)
            .attachsNum(UPDATED_ATTACHS_NUM)
            .seqNo(UPDATED_SEQ_NO)
            .enable(UPDATED_ENABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompany)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyLongName()).isEqualTo(UPDATED_COMPANY_LONG_NAME);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getParentCompanyName()).isEqualTo(UPDATED_PARENT_COMPANY_NAME);
        assertThat(testCompany.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompany.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCompany.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testCompany.getAddressCode()).isEqualTo(UPDATED_ADDRESS_CODE);
        assertThat(testCompany.getAddressName()).isEqualTo(UPDATED_ADDRESS_NAME);
        assertThat(testCompany.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCompany.getLegalPerson()).isEqualTo(UPDATED_LEGAL_PERSON);
        assertThat(testCompany.getParentCompanyCode()).isEqualTo(UPDATED_PARENT_COMPANY_CODE);
        assertThat(testCompany.getLevelId()).isEqualTo(UPDATED_LEVEL_ID);
        assertThat(testCompany.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCompany.getAttachsNum()).isEqualTo(UPDATED_ATTACHS_NUM);
        assertThat(testCompany.getSeqNo()).isEqualTo(UPDATED_SEQ_NO);
        assertThat(testCompany.getEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testCompany.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCompany.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testCompany.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCompany.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

//    @Test
//    public void mytest(){
//        List <Company> companies = companyRepository.("a","a");
//        System.out.println(companies.size());
//    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyService.save(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Get the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company.class);
        Company company1 = new Company();
        company1.setId(1L);
        Company company2 = new Company();
        company2.setId(company1.getId());
        assertThat(company1).isEqualTo(company2);
        company2.setId(2L);
        assertThat(company1).isNotEqualTo(company2);
        company1.setId(null);
        assertThat(company1).isNotEqualTo(company2);
    }

   

//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Company.class);
//        Company company1 = new Company(cityName);
//        company1.setId(1L);
//        Company company2 = new Company(cityName);
//        company2.setId(company1.getId());
//        assertThat(company1).isEqualTo(company2);
//        company2.setId(2L);
//        assertThat(company1).isNotEqualTo(company2);
//        company1.setId(null);
//        assertThat(company1).isNotEqualTo(company2);
//    }
}
