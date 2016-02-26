package com.noptech.vcta.jhipster.service;

import com.noptech.vcta.jhipster.domain.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Trip.
 */
public interface TripService {

    /**
     * Save a trip.
     * @return the persisted entity
     */
    public Trip save(Trip trip);

    /**
     *  get all the trips.
     *  @return the list of entities
     */
    public Page<Trip> findAll(Pageable pageable);

    /**
     *  get the "id" trip.
     *  @return the entity
     */
    public Trip findOne(Long id);

    /**
     *  delete the "id" trip.
     */
    public void delete(Long id);
}
