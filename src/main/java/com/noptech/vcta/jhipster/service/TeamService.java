package com.noptech.vcta.jhipster.service;

import com.noptech.vcta.jhipster.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Team.
 */
public interface TeamService {

    /**
     * Save a team.
     * @return the persisted entity
     */
    public Team save(Team team);

    /**
     *  get all the teams.
     *  @return the list of entities
     */
    public Page<Team> findAll(Pageable pageable);

    /**
     *  get the "id" team.
     *  @return the entity
     */
    public Team findOne(Long id);

    /**
     *  delete the "id" team.
     */
    public void delete(Long id);
}
