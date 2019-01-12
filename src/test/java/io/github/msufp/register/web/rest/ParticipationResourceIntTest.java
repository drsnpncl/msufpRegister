package io.github.msufp.register.web.rest;

import io.github.msufp.register.MsufpRegisterApp;

import io.github.msufp.register.domain.Participation;
import io.github.msufp.register.repository.ParticipationRepository;
import io.github.msufp.register.service.ParticipationService;
import io.github.msufp.register.service.dto.ParticipationDTO;
import io.github.msufp.register.service.mapper.ParticipationMapper;
import io.github.msufp.register.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static io.github.msufp.register.web.rest.TestUtil.sameInstant;
import static io.github.msufp.register.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParticipationResource REST controller.
 *
 * @see ParticipationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MsufpRegisterApp.class)
public class ParticipationResourceIntTest {

    private static final ZonedDateTime DEFAULT_REG_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REG_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PAYMENT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_MODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PAYMENT_ACCEPT = false;
    private static final Boolean UPDATED_PAYMENT_ACCEPT = true;

    private static final Boolean DEFAULT_ABSTRACT_SELECT = false;
    private static final Boolean UPDATED_ABSTRACT_SELECT = true;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private ParticipationMapper participationMapper;

    @Autowired
    private ParticipationService participationService;

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

    private MockMvc restParticipationMockMvc;

    private Participation participation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParticipationResource participationResource = new ParticipationResource(participationService);
        this.restParticipationMockMvc = MockMvcBuilders.standaloneSetup(participationResource)
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
    public static Participation createEntity(EntityManager em) {
        Participation participation = new Participation()
            .regDate(DEFAULT_REG_DATE)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .paymentAccept(DEFAULT_PAYMENT_ACCEPT)
            .abstractSelect(DEFAULT_ABSTRACT_SELECT);
        return participation;
    }

    @Before
    public void initTest() {
        participation = createEntity(em);
    }

    @Test
    @Transactional
    public void createParticipation() throws Exception {
        int databaseSizeBeforeCreate = participationRepository.findAll().size();

        // Create the Participation
        ParticipationDTO participationDTO = participationMapper.toDto(participation);
        restParticipationMockMvc.perform(post("/api/participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participationDTO)))
            .andExpect(status().isCreated());

        // Validate the Participation in the database
        List<Participation> participationList = participationRepository.findAll();
        assertThat(participationList).hasSize(databaseSizeBeforeCreate + 1);
        Participation testParticipation = participationList.get(participationList.size() - 1);
        assertThat(testParticipation.getRegDate()).isEqualTo(DEFAULT_REG_DATE);
        assertThat(testParticipation.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testParticipation.isPaymentAccept()).isEqualTo(DEFAULT_PAYMENT_ACCEPT);
        assertThat(testParticipation.isAbstractSelect()).isEqualTo(DEFAULT_ABSTRACT_SELECT);
    }

    @Test
    @Transactional
    public void createParticipationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = participationRepository.findAll().size();

        // Create the Participation with an existing ID
        participation.setId(1L);
        ParticipationDTO participationDTO = participationMapper.toDto(participation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipationMockMvc.perform(post("/api/participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Participation in the database
        List<Participation> participationList = participationRepository.findAll();
        assertThat(participationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParticipations() throws Exception {
        // Initialize the database
        participationRepository.saveAndFlush(participation);

        // Get all the participationList
        restParticipationMockMvc.perform(get("/api/participations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participation.getId().intValue())))
            .andExpect(jsonPath("$.[*].regDate").value(hasItem(sameInstant(DEFAULT_REG_DATE))))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].paymentAccept").value(hasItem(DEFAULT_PAYMENT_ACCEPT.booleanValue())))
            .andExpect(jsonPath("$.[*].abstractSelect").value(hasItem(DEFAULT_ABSTRACT_SELECT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getParticipation() throws Exception {
        // Initialize the database
        participationRepository.saveAndFlush(participation);

        // Get the participation
        restParticipationMockMvc.perform(get("/api/participations/{id}", participation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participation.getId().intValue()))
            .andExpect(jsonPath("$.regDate").value(sameInstant(DEFAULT_REG_DATE)))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.paymentAccept").value(DEFAULT_PAYMENT_ACCEPT.booleanValue()))
            .andExpect(jsonPath("$.abstractSelect").value(DEFAULT_ABSTRACT_SELECT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParticipation() throws Exception {
        // Get the participation
        restParticipationMockMvc.perform(get("/api/participations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParticipation() throws Exception {
        // Initialize the database
        participationRepository.saveAndFlush(participation);

        int databaseSizeBeforeUpdate = participationRepository.findAll().size();

        // Update the participation
        Participation updatedParticipation = participationRepository.findById(participation.getId()).get();
        // Disconnect from session so that the updates on updatedParticipation are not directly saved in db
        em.detach(updatedParticipation);
        updatedParticipation
            .regDate(UPDATED_REG_DATE)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .paymentAccept(UPDATED_PAYMENT_ACCEPT)
            .abstractSelect(UPDATED_ABSTRACT_SELECT);
        ParticipationDTO participationDTO = participationMapper.toDto(updatedParticipation);

        restParticipationMockMvc.perform(put("/api/participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participationDTO)))
            .andExpect(status().isOk());

        // Validate the Participation in the database
        List<Participation> participationList = participationRepository.findAll();
        assertThat(participationList).hasSize(databaseSizeBeforeUpdate);
        Participation testParticipation = participationList.get(participationList.size() - 1);
        assertThat(testParticipation.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testParticipation.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testParticipation.isPaymentAccept()).isEqualTo(UPDATED_PAYMENT_ACCEPT);
        assertThat(testParticipation.isAbstractSelect()).isEqualTo(UPDATED_ABSTRACT_SELECT);
    }

    @Test
    @Transactional
    public void updateNonExistingParticipation() throws Exception {
        int databaseSizeBeforeUpdate = participationRepository.findAll().size();

        // Create the Participation
        ParticipationDTO participationDTO = participationMapper.toDto(participation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipationMockMvc.perform(put("/api/participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Participation in the database
        List<Participation> participationList = participationRepository.findAll();
        assertThat(participationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParticipation() throws Exception {
        // Initialize the database
        participationRepository.saveAndFlush(participation);

        int databaseSizeBeforeDelete = participationRepository.findAll().size();

        // Get the participation
        restParticipationMockMvc.perform(delete("/api/participations/{id}", participation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Participation> participationList = participationRepository.findAll();
        assertThat(participationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participation.class);
        Participation participation1 = new Participation();
        participation1.setId(1L);
        Participation participation2 = new Participation();
        participation2.setId(participation1.getId());
        assertThat(participation1).isEqualTo(participation2);
        participation2.setId(2L);
        assertThat(participation1).isNotEqualTo(participation2);
        participation1.setId(null);
        assertThat(participation1).isNotEqualTo(participation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipationDTO.class);
        ParticipationDTO participationDTO1 = new ParticipationDTO();
        participationDTO1.setId(1L);
        ParticipationDTO participationDTO2 = new ParticipationDTO();
        assertThat(participationDTO1).isNotEqualTo(participationDTO2);
        participationDTO2.setId(participationDTO1.getId());
        assertThat(participationDTO1).isEqualTo(participationDTO2);
        participationDTO2.setId(2L);
        assertThat(participationDTO1).isNotEqualTo(participationDTO2);
        participationDTO1.setId(null);
        assertThat(participationDTO1).isNotEqualTo(participationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(participationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(participationMapper.fromId(null)).isNull();
    }
}
