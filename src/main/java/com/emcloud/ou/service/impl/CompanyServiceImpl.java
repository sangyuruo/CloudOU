package com.emcloud.ou.service.impl;

import com.emcloud.ou.security.SecurityUtils;
import com.emcloud.ou.service.CompanyService;
import com.emcloud.ou.domain.Company;
import com.emcloud.ou.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService{

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Save a company.
     *
     * @param company the entity to save
     * @return the persisted entity
     */
    @Override
    public Company save(Company company) {
        log.debug("Request to save Company : {}", company);
        company.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        company.setCreateTime(Instant.now());
        company.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        company.setUpdateTime(Instant.now());
        return companyRepository.save(company);
    }

    /**
     * Update a company.
     *
     * @param company the entity to update
     * @return the persisted entity
     */
    @Override
    public Company update(Company company) {
        log.debug("Request to save Company : {}", company);
        company.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        company.setUpdateTime(Instant.now());
        return companyRepository.save(company);
    }

    /**
     *  Get all the companies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Company> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable);
    }

    @Override
    public List<Company> findAll() {
        log.debug("Request to get all Companies");
        return companyRepository.findAll();
    }


    /**
     *  Get all the companies by companyname .
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Company> findByCOrA(String companyname,String addressname) {
        log.debug("Request to get all Companies by companyname");
        return companyRepository.findByCompanyNameContainingOrAddressNameContaining(companyname,addressname);
    }


    /**
     *  Get one company by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Company findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findOne(id);
    }

    /**
     *  Delete the  company by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
    }
}
