package com.emcloud.ou.web.rest;

import com.emcloud.ou.EmCloudOuApp;

import com.emcloud.ou.config.SecurityBeanOverrideConfiguration;

import com.emcloud.ou.domain.UserEmp;
import com.emcloud.ou.repository.UserEmpRepository;
import com.emcloud.ou.service.UserEmpService;
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
import java.util.List;

import static com.emcloud.ou.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserEmpResource REST controller.
 *
 * @see UserEmpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudOuApp.class, SecurityBeanOverrideConfiguration.class})
public class UserEmpResourceIntTest {

    private static final String DEFAULT_ECODE = "AAAAAAAAAA";
    private static final String UPDATED_ECODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    @Autowired
    private UserEmpRepository userEmpRepository;

    @Autowired
    private UserEmpService userEmpService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserEmpMockMvc;

    private UserEmp userEmp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserEmpResource userEmpResource = new UserEmpResource(userEmpService);
        this.restUserEmpMockMvc = MockMvcBuilders.standaloneSetup(userEmpResource)
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
    public static UserEmp createEntity(EntityManager em) {
        UserEmp userEmp = new UserEmp()
            .ecode(DEFAULT_ECODE)
            .login(DEFAULT_LOGIN);
        return userEmp;
    }

    @Before
    public void initTest() {
        userEmp = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserEmp() throws Exception {
        int databaseSizeBeforeCreate = userEmpRepository.findAll().size();

        // Create the UserEmp
        restUserEmpMockMvc.perform(post("/api/user-emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userEmp)))
            .andExpect(status().isCreated());

        // Validate the UserEmp in the database
        List<UserEmp> userEmpList = userEmpRepository.findAll();
        assertThat(userEmpList).hasSize(databaseSizeBeforeCreate + 1);
        UserEmp testUserEmp = userEmpList.get(userEmpList.size() - 1);
        assertThat(testUserEmp.getEcode()).isEqualTo(DEFAULT_ECODE);
        assertThat(testUserEmp.getLogin()).isEqualTo(DEFAULT_LOGIN);
    }

    @Test
    @Transactional
    public void createUserEmpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userEmpRepository.findAll().size();

        // Create the UserEmp with an existing ID
        userEmp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserEmpMockMvc.perform(post("/api/user-emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userEmp)))
            .andExpect(status().isBadRequest());

        // Validate the UserEmp in the database
        List<UserEmp> userEmpList = userEmpRepository.findAll();
        assertThat(userEmpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userEmpRepository.findAll().size();
        // set the field null
        userEmp.setEcode(null);

        // Create the UserEmp, which fails.

        restUserEmpMockMvc.perform(post("/api/user-emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userEmp)))
            .andExpect(status().isBadRequest());

        List<UserEmp> userEmpList = userEmpRepository.findAll();
        assertThat(userEmpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = userEmpRepository.findAll().size();
        // set the field null
        userEmp.setLogin(null);

        // Create the UserEmp, which fails.

        restUserEmpMockMvc.perform(post("/api/user-emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userEmp)))
            .andExpect(status().isBadRequest());

        List<UserEmp> userEmpList = userEmpRepository.findAll();
        assertThat(userEmpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserEmps() throws Exception {
        // Initialize the database
        userEmpRepository.saveAndFlush(userEmp);

        // Get all the userEmpList
        restUserEmpMockMvc.perform(get("/api/user-emps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userEmp.getId().intValue())))
            .andExpect(jsonPath("$.[*].ecode").value(hasItem(DEFAULT_ECODE.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())));
    }

    @Test
    @Transactional
    public void getUserEmp() throws Exception {
        // Initialize the database
        userEmpRepository.saveAndFlush(userEmp);

        // Get the userEmp
        restUserEmpMockMvc.perform(get("/api/user-emps/{id}", userEmp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userEmp.getId().intValue()))
            .andExpect(jsonPath("$.ecode").value(DEFAULT_ECODE.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserEmp() throws Exception {
        // Get the userEmp
        restUserEmpMockMvc.perform(get("/api/user-emps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserEmp() throws Exception {
        // Initialize the database
        userEmpService.save(userEmp);

        int databaseSizeBeforeUpdate = userEmpRepository.findAll().size();

        // Update the userEmp
        UserEmp updatedUserEmp = userEmpRepository.findOne(userEmp.getId());
        updatedUserEmp
            .ecode(UPDATED_ECODE)
            .login(UPDATED_LOGIN);

        restUserEmpMockMvc.perform(put("/api/user-emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserEmp)))
            .andExpect(status().isOk());

        // Validate the UserEmp in the database
        List<UserEmp> userEmpList = userEmpRepository.findAll();
        assertThat(userEmpList).hasSize(databaseSizeBeforeUpdate);
        UserEmp testUserEmp = userEmpList.get(userEmpList.size() - 1);
        assertThat(testUserEmp.getEcode()).isEqualTo(UPDATED_ECODE);
        assertThat(testUserEmp.getLogin()).isEqualTo(UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void updateNonExistingUserEmp() throws Exception {
        int databaseSizeBeforeUpdate = userEmpRepository.findAll().size();

        // Create the UserEmp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserEmpMockMvc.perform(put("/api/user-emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userEmp)))
            .andExpect(status().isCreated());

        // Validate the UserEmp in the database
        List<UserEmp> userEmpList = userEmpRepository.findAll();
        assertThat(userEmpList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserEmp() throws Exception {
        // Initialize the database
        userEmpService.save(userEmp);

        int databaseSizeBeforeDelete = userEmpRepository.findAll().size();

        // Get the userEmp
        restUserEmpMockMvc.perform(delete("/api/user-emps/{id}", userEmp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserEmp> userEmpList = userEmpRepository.findAll();
        assertThat(userEmpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserEmp.class);
        UserEmp userEmp1 = new UserEmp();
        userEmp1.setId(1L);
        UserEmp userEmp2 = new UserEmp();
        userEmp2.setId(userEmp1.getId());
        assertThat(userEmp1).isEqualTo(userEmp2);
        userEmp2.setId(2L);
        assertThat(userEmp1).isNotEqualTo(userEmp2);
        userEmp1.setId(null);
        assertThat(userEmp1).isNotEqualTo(userEmp2);
    }
}
