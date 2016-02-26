package com.noptech.vcta.jhipster.repository;

import com.noptech.vcta.jhipster.domain.Trip;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Trip entity.
 */
public interface TripRepository extends JpaRepository<Trip,Long> {

}
