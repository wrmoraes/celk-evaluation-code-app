package br.com.celk.evaluation.ufengine.web.rest;

import br.com.celk.evaluation.ufengine.CelkApp;
import br.com.celk.evaluation.ufengine.domain.County;
import br.com.celk.evaluation.ufengine.repository.CountyRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CountyResource} REST controller.
 */
@SpringBootTest(classes = CelkApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CountyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CountyRepository countyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountyMockMvc;

    private County county;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static County createEntity(EntityManager em) {
        County county = new County()
            .name(DEFAULT_NAME)
            .createdDate(DEFAULT_CREATED_DATE);
        return county;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static County createUpdatedEntity(EntityManager em) {
        County county = new County()
            .name(UPDATED_NAME)
            .createdDate(UPDATED_CREATED_DATE);
        return county;
    }

    @BeforeEach
    public void initTest() {
        county = createEntity(em);
    }

    @Test
    @Transactional
    public void createCounty() throws Exception {
        int databaseSizeBeforeCreate = countyRepository.findAll().size();

        // Create the County
        restCountyMockMvc.perform(post("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(county)))
            .andExpect(status().isCreated());

        // Validate the County in the database
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeCreate + 1);
        County testCounty = countyList.get(countyList.size() - 1);
        assertThat(testCounty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCounty.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createCountyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countyRepository.findAll().size();

        // Create the County with an existing ID
        county.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountyMockMvc.perform(post("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(county)))
            .andExpect(status().isBadRequest());

        // Validate the County in the database
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countyRepository.findAll().size();
        // set the field null
        county.setName(null);

        // Create the County, which fails.

        restCountyMockMvc.perform(post("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(county)))
            .andExpect(status().isBadRequest());

        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = countyRepository.findAll().size();
        // set the field null
        county.setCreatedDate(null);

        // Create the County, which fails.

        restCountyMockMvc.perform(post("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(county)))
            .andExpect(status().isBadRequest());

        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCounties() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the countyList
        restCountyMockMvc.perform(get("/api/counties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(county.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get the county
        restCountyMockMvc.perform(get("/api/counties/{id}", county.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(county.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCounty() throws Exception {
        // Get the county
        restCountyMockMvc.perform(get("/api/counties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        int databaseSizeBeforeUpdate = countyRepository.findAll().size();

        // Update the county
        County updatedCounty = countyRepository.findById(county.getId()).get();
        // Disconnect from session so that the updates on updatedCounty are not directly saved in db
        em.detach(updatedCounty);
        updatedCounty
            .name(UPDATED_NAME)
            .createdDate(UPDATED_CREATED_DATE);

        restCountyMockMvc.perform(put("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCounty)))
            .andExpect(status().isOk());

        // Validate the County in the database
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeUpdate);
        County testCounty = countyList.get(countyList.size() - 1);
        assertThat(testCounty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCounty.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCounty() throws Exception {
        int databaseSizeBeforeUpdate = countyRepository.findAll().size();

        // Create the County

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountyMockMvc.perform(put("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(county)))
            .andExpect(status().isBadRequest());

        // Validate the County in the database
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        int databaseSizeBeforeDelete = countyRepository.findAll().size();

        // Delete the county
        restCountyMockMvc.perform(delete("/api/counties/{id}", county.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
