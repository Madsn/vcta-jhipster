package com.noptech.vcta.jhipster.web.rest;

import com.noptech.vcta.jhipster.Application;
import com.noptech.vcta.jhipster.domain.Trip;
import com.noptech.vcta.jhipster.repository.TripRepository;
import com.noptech.vcta.jhipster.service.TripService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TripResource REST controller.
 *
 * @see TripResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TripResourceIntTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_DISTANCE = 0D;
    private static final Double UPDATED_DISTANCE = 1D;

    @Inject
    private TripRepository tripRepository;

    @Inject
    private TripService tripService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTripMockMvc;

    private Trip trip;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripResource tripResource = new TripResource();
        ReflectionTestUtils.setField(tripResource, "tripService", tripService);
        this.restTripMockMvc = MockMvcBuilders.standaloneSetup(tripResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trip = new Trip();
        trip.setDate(DEFAULT_DATE);
        trip.setDistance(DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = trips.get(trips.size() - 1);
        assertThat(testTrip.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTrip.getDistance()).isEqualTo(DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDate(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isBadRequest());

        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setDistance(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isBadRequest());

        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the trips
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())));
    }

    @Test
    @Transactional
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trip.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

		int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        trip.setDate(UPDATED_DATE);
        trip.setDistance(UPDATED_DISTANCE);

        restTripMockMvc.perform(put("/api/trips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trip)))
                .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = trips.get(trips.size() - 1);
        assertThat(testTrip.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTrip.getDistance()).isEqualTo(UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

		int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Get the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(databaseSizeBeforeDelete - 1);
    }
}
