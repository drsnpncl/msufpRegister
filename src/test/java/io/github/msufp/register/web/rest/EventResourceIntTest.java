package io.github.msufp.register.web.rest;

import io.github.msufp.register.MsufpRegisterApp;

import io.github.msufp.register.domain.Event;
import io.github.msufp.register.repository.EventRepository;
import io.github.msufp.register.service.EventService;
import io.github.msufp.register.service.dto.EventDTO;
import io.github.msufp.register.service.mapper.EventMapper;
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
import java.util.List;


import static io.github.msufp.register.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MsufpRegisterApp.class)
public class EventResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_FEE = 1;
    private static final Integer UPDATED_FEE = 2;

    private static final String DEFAULT_NUMBER_OF_MEMBERS = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_OF_MEMBERS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_OF_PARTICIPATION = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_OF_PARTICIPATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FREE_EVENT = false;
    private static final Boolean UPDATED_FREE_EVENT = true;

    private static final Boolean DEFAULT_ABSTRACT_SELECT = false;
    private static final Boolean UPDATED_ABSTRACT_SELECT = true;

    private static final Boolean DEFAULT_TEST = false;
    private static final Boolean UPDATED_TEST = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_EVENT = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventService eventService;

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

    private MockMvc restEventMockMvc;

    private Event event;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventResource eventResource = new EventResource(eventService);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
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
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .name(DEFAULT_NAME)
            .fee(DEFAULT_FEE)
            .numberOfMembers(DEFAULT_NUMBER_OF_MEMBERS)
            .typeOfParticipation(DEFAULT_TYPE_OF_PARTICIPATION)
            .freeEvent(DEFAULT_FREE_EVENT)
            .abstractSelect(DEFAULT_ABSTRACT_SELECT)
            .test(DEFAULT_TEST)
            .status(DEFAULT_STATUS)
            .parentEvent(DEFAULT_PARENT_EVENT)
            .eventType(DEFAULT_EVENT_TYPE);
        return event;
    }

    @Before
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvent.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testEvent.getNumberOfMembers()).isEqualTo(DEFAULT_NUMBER_OF_MEMBERS);
        assertThat(testEvent.getTypeOfParticipation()).isEqualTo(DEFAULT_TYPE_OF_PARTICIPATION);
        assertThat(testEvent.isFreeEvent()).isEqualTo(DEFAULT_FREE_EVENT);
        assertThat(testEvent.isAbstractSelect()).isEqualTo(DEFAULT_ABSTRACT_SELECT);
        assertThat(testEvent.isTest()).isEqualTo(DEFAULT_TEST);
        assertThat(testEvent.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEvent.getParentEvent()).isEqualTo(DEFAULT_PARENT_EVENT);
        assertThat(testEvent.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId(1L);
        EventDTO eventDTO = eventMapper.toDto(event);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE)))
            .andExpect(jsonPath("$.[*].numberOfMembers").value(hasItem(DEFAULT_NUMBER_OF_MEMBERS.toString())))
            .andExpect(jsonPath("$.[*].typeOfParticipation").value(hasItem(DEFAULT_TYPE_OF_PARTICIPATION.toString())))
            .andExpect(jsonPath("$.[*].freeEvent").value(hasItem(DEFAULT_FREE_EVENT.booleanValue())))
            .andExpect(jsonPath("$.[*].abstractSelect").value(hasItem(DEFAULT_ABSTRACT_SELECT.booleanValue())))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].parentEvent").value(hasItem(DEFAULT_PARENT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE))
            .andExpect(jsonPath("$.numberOfMembers").value(DEFAULT_NUMBER_OF_MEMBERS.toString()))
            .andExpect(jsonPath("$.typeOfParticipation").value(DEFAULT_TYPE_OF_PARTICIPATION.toString()))
            .andExpect(jsonPath("$.freeEvent").value(DEFAULT_FREE_EVENT.booleanValue()))
            .andExpect(jsonPath("$.abstractSelect").value(DEFAULT_ABSTRACT_SELECT.booleanValue()))
            .andExpect(jsonPath("$.test").value(DEFAULT_TEST.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.parentEvent").value(DEFAULT_PARENT_EVENT.toString()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .numberOfMembers(UPDATED_NUMBER_OF_MEMBERS)
            .typeOfParticipation(UPDATED_TYPE_OF_PARTICIPATION)
            .freeEvent(UPDATED_FREE_EVENT)
            .abstractSelect(UPDATED_ABSTRACT_SELECT)
            .test(UPDATED_TEST)
            .status(UPDATED_STATUS)
            .parentEvent(UPDATED_PARENT_EVENT)
            .eventType(UPDATED_EVENT_TYPE);
        EventDTO eventDTO = eventMapper.toDto(updatedEvent);

        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvent.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testEvent.getNumberOfMembers()).isEqualTo(UPDATED_NUMBER_OF_MEMBERS);
        assertThat(testEvent.getTypeOfParticipation()).isEqualTo(UPDATED_TYPE_OF_PARTICIPATION);
        assertThat(testEvent.isFreeEvent()).isEqualTo(UPDATED_FREE_EVENT);
        assertThat(testEvent.isAbstractSelect()).isEqualTo(UPDATED_ABSTRACT_SELECT);
        assertThat(testEvent.isTest()).isEqualTo(UPDATED_TEST);
        assertThat(testEvent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEvent.getParentEvent()).isEqualTo(UPDATED_PARENT_EVENT);
        assertThat(testEvent.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Get the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);
        event2.setId(2L);
        assertThat(event1).isNotEqualTo(event2);
        event1.setId(null);
        assertThat(event1).isNotEqualTo(event2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventDTO.class);
        EventDTO eventDTO1 = new EventDTO();
        eventDTO1.setId(1L);
        EventDTO eventDTO2 = new EventDTO();
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
        eventDTO2.setId(eventDTO1.getId());
        assertThat(eventDTO1).isEqualTo(eventDTO2);
        eventDTO2.setId(2L);
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
        eventDTO1.setId(null);
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventMapper.fromId(null)).isNull();
    }
}
