package com.emcloud.ou.service;

import com.emcloud.ou.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Company.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param company the entity to save
     * @return the persisted entity
     */
    Company save(Company company);

    /**
     * Update a company.
     *
     * @param company the entity to update
     * @return the persisted entity
     */
    Company update(Company company);

    /**
     *  Get all the companies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Company> findAll(Pageable pageable);

    /**
     *  Get all the companies by companyname.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Company> findByCompanyName(Pageable pageable,String companyname);

    /**
     *  Get the "id" company.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Company findOne(Long id);

    /**
     *  Delete the "id" company.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
