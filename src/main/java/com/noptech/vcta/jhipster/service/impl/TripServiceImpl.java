package com.noptech.vcta.jhipster.service.impl;

import com.noptech.vcta.jhipster.service.TripService;
import com.noptech.vcta.jhipster.domain.Trip;
import com.noptech.vcta.jhipster.repository.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Trip.
 */
@Service
@Transactional
public class TripServiceImpl implements TripService{

    private final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);
    
    @Inject
    private TripRepository tripRepository;
    
    /**
     * Save a trip.
     * @return the persisted entity
     */
    public Trip save(Trip trip) {
        log.debug("Request to save Trip : {}", trip);
        Trip result = tripRepository.save(trip);
        return result;
    }

    /**
     *  get all the trips.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Trip> findAll(Pageable pageable) {
        log.debug("Request to get all Trips");
        Page<Trip> result = tripRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one trip by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Trip findOne(Long id) {
        log.debug("Request to get Trip : {}", id);
        Trip trip = tripRepository.findOne(id);
        return trip;
    }

    /**
     *  delete the  trip by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Trip : {}", id);
        tripRepository.delete(id);
    }
}
