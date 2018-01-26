package com.emcloud.ou.service.impl;

import com.emcloud.ou.security.SecurityUtils;
import com.emcloud.ou.service.OrganizationService;
import com.emcloud.ou.domain.Organization;
import com.emcloud.ou.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Service Implementation for managing Organization.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService{

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


    @Override
    public List<Organization> findAllByCompanyCode(String companyCode) {
        return organizationRepository.findAllByCompanyCode(companyCode);
    }

    @Override
    public List<Organization> findAllByCompanyName(String companyName) {
        return organizationRepository.findAllByCompanyName(companyName);
    }

    /**
     * Save a organization.
     *
     * @param organization the entity to save
     * @return the persisted entity
     */
    @Override
    public Organization save(Organization organization) {
        log.debug("Request to save Organization : {}", organization);
        organization.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        organization.setCreateTime(Instant.now());
        organization.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        organization.setUpdateTime(Instant.now());
        if (checked(organization.getParentCode(),organization.getOrgCode())){
            organization.setOrgCode(organization.getOrgCode());
            return organizationRepository.save(organization);
        }else {
            return null;
        }
    }
    public static boolean checked(String fzz,String zz){
        boolean bool = Pattern.matches("^"+fzz+".*", zz);
        return bool;
    }
    /**
     * Update a organization.
     *
     * @param organization the entity to update
     * @return the persisted entity
     */
    @Override
    public Organization update(Organization organization) {
        log.debug("Request to save Organization : {}", organization);
        organization.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        organization.setUpdateTime(Instant.now());
        return organizationRepository.save(organization);
    }

    /**
     *  Get all the organizations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Organization> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll(pageable);
    }

    /**
     *  Get all the organizations.
     *
     *  @param orgName the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Organization> findByOrgName(String orgName) {
        log.debug("Request to get all Organization by orgName");
        return organizationRepository.findByOrgName(orgName);
    }
    /**
     *  Get one organization by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Organization findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findOne(id);
    }

    /**
     *  Delete the  organization by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.delete(id);
    }
}
