package com.noptech.vcta.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.noptech.vcta.jhipster.domain.Trip;
import com.noptech.vcta.jhipster.service.TripService;
import com.noptech.vcta.jhipster.web.rest.util.HeaderUtil;
import com.noptech.vcta.jhipster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Trip.
 */
@RestController
@RequestMapping("/api")
public class TripResource {

    private final Logger log = LoggerFactory.getLogger(TripResource.class);
        
    @Inject
    private TripService tripService;
    
    /**
     * POST  /trips -> Create a new trip.
     */
    @RequestMapping(value = "/trips",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trip> createTrip(@Valid @RequestBody Trip trip) throws URISyntaxException {
        log.debug("REST request to save Trip : {}", trip);
        if (trip.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("trip", "idexists", "A new trip cannot already have an ID")).body(null);
        }
        Trip result = tripService.save(trip);
        return ResponseEntity.created(new URI("/api/trips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trip", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trips -> Updates an existing trip.
     */
    @RequestMapping(value = "/trips",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trip> updateTrip(@Valid @RequestBody Trip trip) throws URISyntaxException {
        log.debug("REST request to update Trip : {}", trip);
        if (trip.getId() == null) {
            return createTrip(trip);
        }
        Trip result = tripService.save(trip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trip", trip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trips -> get all the trips.
     */
    @RequestMapping(value = "/trips",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Trip>> getAllTrips(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Trips");
        Page<Trip> page = tripService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trips");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trips/:id -> get the "id" trip.
     */
    @RequestMapping(value = "/trips/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trip> getTrip(@PathVariable Long id) {
        log.debug("REST request to get Trip : {}", id);
        Trip trip = tripService.findOne(id);
        return Optional.ofNullable(trip)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trips/:id -> delete the "id" trip.
     */
    @RequestMapping(value = "/trips/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        log.debug("REST request to delete Trip : {}", id);
        tripService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trip", id.toString())).build();
    }
}
