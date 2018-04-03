package com.emcloud.ou.service.impl;

import com.emcloud.ou.service.UserEmpService;
import com.emcloud.ou.domain.UserEmp;
import com.emcloud.ou.repository.UserEmpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserEmp.
 */
@Service
@Transactional
public class UserEmpServiceImpl implements UserEmpService{

    private final Logger log = LoggerFactory.getLogger(UserEmpServiceImpl.class);

    private final UserEmpRepository userEmpRepository;

    public UserEmpServiceImpl(UserEmpRepository userEmpRepository) {
        this.userEmpRepository = userEmpRepository;
    }

    /**
     * Save a userEmp.
     *
     * @param userEmp the entity to save
     * @return the persisted entity
     */
    @Override
    public UserEmp save(UserEmp userEmp) {
        log.debug("Request to save UserEmp : {}", userEmp);
        return userEmpRepository.save(userEmp);
    }

    /**
     *  Get all the userEmps.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserEmp> findAll(Pageable pageable) {
        log.debug("Request to get all UserEmps");
        return userEmpRepository.findAll(pageable);
    }

    /**
     *  Get one userEmp by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserEmp findOne(Long id) {
        log.debug("Request to get UserEmp : {}", id);
        return userEmpRepository.findOne(id);
    }

    /**
     *  Delete the  userEmp by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserEmp : {}", id);
        userEmpRepository.delete(id);
    }
}
