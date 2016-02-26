package com.noptech.vcta.jhipster.service.impl;

import com.noptech.vcta.jhipster.service.TeamService;
import com.noptech.vcta.jhipster.domain.Team;
import com.noptech.vcta.jhipster.repository.TeamRepository;
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
 * Service Implementation for managing Team.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService{

    private final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);
    
    @Inject
    private TeamRepository teamRepository;
    
    /**
     * Save a team.
     * @return the persisted entity
     */
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        Team result = teamRepository.save(team);
        return result;
    }

    /**
     *  get all the teams.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Team> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        Page<Team> result = teamRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one team by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Team findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        Team team = teamRepository.findOne(id);
        return team;
    }

    /**
     *  delete the  team by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.delete(id);
    }
}
