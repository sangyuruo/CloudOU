package com.emcloud.ou.service;

import com.emcloud.ou.domain.UserEmp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserEmp.
 */
public interface UserEmpService {

    /**
     * Save a userEmp.
     *
     * @param userEmp the entity to save
     * @return the persisted entity
     */
    UserEmp save(UserEmp userEmp);

    /**
     *  Get all the userEmps.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserEmp> findAll(Pageable pageable);

    /**
     *  Get the "id" userEmp.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserEmp findOne(Long id);

    /**
     *  Delete the "id" userEmp.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    UserEmp findOneByLogin(String login);
}
