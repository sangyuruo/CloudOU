package com.emcloud.ou.web.rest;

import com.emcloud.ou.EmCloudOuApp;

import com.emcloud.ou.config.SecurityBeanOverrideConfiguration;

import com.emcloud.ou.domain.Organization;
import com.emcloud.ou.repository.OrganizationRepository;
import com.emcloud.ou.service.OrganizationService;
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
 * Test class for the OrganizationResource REST controller.
 *
 * @see OrganizationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudOuApp.class, SecurityBeanOverrideConfiguration.class})
public class OrganizationResourceIntTest {

    private static final String DEFAULT_ORG_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORG_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORG_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQ_NO = 1;
    private static final Integer UPDATED_SEQ_NO = 2;

    private static final Integer DEFAULT_ATTACHS_NUM = 1;
    private static final Integer UPDATED_ATTACHS_NUM = 2;

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
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationResource organizationResource = new OrganizationResource(organizationService);
        this.restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource)
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
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .orgCode(DEFAULT_ORG_CODE)
            .orgName(DEFAULT_ORG_NAME)
            .orgType(DEFAULT_ORG_TYPE)
            .companyCode(DEFAULT_COMPANY_CODE)
            .parentCode(DEFAULT_PARENT_CODE)
            .telephone(DEFAULT_TELEPHONE)
            .companyName(DEFAULT_COMPANY_NAME)
            .parentOrgName(DEFAULT_PARENT_ORG_NAME)
            .addressName(DEFAULT_ADDRESS_NAME)
            .addressCode(DEFAULT_ADDRESS_CODE)
            .remark(DEFAULT_REMARK)
            .seqNo(DEFAULT_SEQ_NO)
            .attachsNum(DEFAULT_ATTACHS_NUM)
            .enable(DEFAULT_ENABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return organization;
    }

    @Before
    public void initTest() {
        organization = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrgCode()).isEqualTo(DEFAULT_ORG_CODE);
        assertThat(testOrganization.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testOrganization.getOrgType()).isEqualTo(DEFAULT_ORG_TYPE);
        assertThat(testOrganization.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testOrganization.getParentCode()).isEqualTo(DEFAULT_PARENT_CODE);
        assertThat(testOrganization.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testOrganization.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testOrganization.getParentOrgName()).isEqualTo(DEFAULT_PARENT_ORG_NAME);
        assertThat(testOrganization.getAddressName()).isEqualTo(DEFAULT_ADDRESS_NAME);
        assertThat(testOrganization.getAddressCode()).isEqualTo(DEFAULT_ADDRESS_CODE);
        assertThat(testOrganization.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testOrganization.getSeqNo()).isEqualTo(DEFAULT_SEQ_NO);
        assertThat(testOrganization.getAttachsNum()).isEqualTo(DEFAULT_ATTACHS_NUM);
        assertThat(testOrganization.getEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testOrganization.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrganization.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testOrganization.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOrganization.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization with an existing ID
        organization.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrgCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setOrgCode(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrgNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setOrgName(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setCompanyCode(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setCompanyName(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParentOrgNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setParentOrgName(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setAddressName(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setEnable(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setCreatedBy(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setCreateTime(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setUpdatedBy(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setUpdateTime(null);

        // Create the Organization, which fails.

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc.perform(get("/api/organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgCode").value(hasItem(DEFAULT_ORG_CODE.toString())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME.toString())))
            .andExpect(jsonPath("$.[*].orgType").value(hasItem(DEFAULT_ORG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].parentCode").value(hasItem(DEFAULT_PARENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentOrgName").value(hasItem(DEFAULT_PARENT_ORG_NAME.toString())))
            .andExpect(jsonPath("$.[*].addressName").value(hasItem(DEFAULT_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].addressCode").value(hasItem(DEFAULT_ADDRESS_CODE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].sort").value(hasItem(DEFAULT_SEQ_NO)))
            .andExpect(jsonPath("$.[*].attachsNum").value(hasItem(DEFAULT_ATTACHS_NUM)))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.orgCode").value(DEFAULT_ORG_CODE.toString()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME.toString()))
            .andExpect(jsonPath("$.orgType").value(DEFAULT_ORG_TYPE.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.parentCode").value(DEFAULT_PARENT_CODE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.parentOrgName").value(DEFAULT_PARENT_ORG_NAME.toString()))
            .andExpect(jsonPath("$.addressName").value(DEFAULT_ADDRESS_NAME.toString()))
            .andExpect(jsonPath("$.addressCode").value(DEFAULT_ADDRESS_CODE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.sort").value(DEFAULT_SEQ_NO))
            .andExpect(jsonPath("$.attachsNum").value(DEFAULT_ATTACHS_NUM))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationService.save(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findOne(organization.getId());
        updatedOrganization
            .orgCode(UPDATED_ORG_CODE)
            .orgName(UPDATED_ORG_NAME)
            .orgType(UPDATED_ORG_TYPE)
            .companyCode(UPDATED_COMPANY_CODE)
            .parentCode(UPDATED_PARENT_CODE)
            .telephone(UPDATED_TELEPHONE)
            .companyName(UPDATED_COMPANY_NAME)
            .parentOrgName(UPDATED_PARENT_ORG_NAME)
            .addressName(UPDATED_ADDRESS_NAME)
            .addressCode(UPDATED_ADDRESS_CODE)
            .remark(UPDATED_REMARK)
            .seqNo(UPDATED_SEQ_NO)
            .attachsNum(UPDATED_ATTACHS_NUM)
            .enable(UPDATED_ENABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganization)))
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrgCode()).isEqualTo(UPDATED_ORG_CODE);
        assertThat(testOrganization.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrganization.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
        assertThat(testOrganization.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testOrganization.getParentCode()).isEqualTo(UPDATED_PARENT_CODE);
        assertThat(testOrganization.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testOrganization.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testOrganization.getParentOrgName()).isEqualTo(UPDATED_PARENT_ORG_NAME);
        assertThat(testOrganization.getAddressName()).isEqualTo(UPDATED_ADDRESS_NAME);
        assertThat(testOrganization.getAddressCode()).isEqualTo(UPDATED_ADDRESS_CODE);
        assertThat(testOrganization.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testOrganization.getSeqNo()).isEqualTo(UPDATED_SEQ_NO);
        assertThat(testOrganization.getAttachsNum()).isEqualTo(UPDATED_ATTACHS_NUM);
        assertThat(testOrganization.getEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testOrganization.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganization.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testOrganization.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrganization.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Create the Organization

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationService.save(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Get the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organization.class);
        Organization organization1 = new Organization();
        organization1.setId(1L);
        Organization organization2 = new Organization();
        organization2.setId(organization1.getId());
        assertThat(organization1).isEqualTo(organization2);
        organization2.setId(2L);
        assertThat(organization1).isNotEqualTo(organization2);
        organization1.setId(null);
        assertThat(organization1).isNotEqualTo(organization2);
    }
}
