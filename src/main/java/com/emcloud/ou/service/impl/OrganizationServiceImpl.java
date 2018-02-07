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
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Service Implementation for managing Organization.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

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
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    public List<Organization> findByOrgCode(String orgCode) {
        return organizationRepository.findAllByOrgCode(orgCode);
    }

    @Override
    public List<Organization> findByPOrgCode(String PorgCode) {
        return organizationRepository.findAllByParentCode(PorgCode);
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

        //   organization.setCompanyCode(UUID.randomUUID().toString());

        organization.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        organization.setCreateTime(Instant.now());
        organization.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        organization.setUpdateTime(Instant.now());
        if (checked(organization.getParentCode(), organization.getOrgCode())) {
            organization.setOrgCode(organization.getOrgCode());
            return organizationRepository.save(organization);
        } else {
            return null;
        }


    }

    private static int getLevelNum(Organization org) {
        return org.getOrgCode().length() / 2;
    }

    @Override
    public StringBuilder findtree(String companyCode) {
        int lastLevelNum = 0; // 上一次的层次
        int curLevelNum = 0; // 本次对象的层次

        List<Organization> roots = findByPOrgCode("0");
        // Map<String, Object> data = new HashMap<String, Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        try {//查询所有菜单

            Organization preNav = null;
            for (Organization nav : roots) {
                curLevelNum = getLevelNum(nav);
                if (null != preNav) {
                    if (lastLevelNum == curLevelNum) { // 同一层次的
                        sb.append("}, \n");
                    } else if (lastLevelNum > curLevelNum) { // 这次的层次比上次高一层，也即跳到上一层
                        sb.append("} \n");

                        for (int j = curLevelNum; j < lastLevelNum; j++) {
                            sb.append("]} \n");
                            if (j == lastLevelNum - 1) {
                                sb.append(", \n");
                            }

                        }
                    }
                }
                sb.append("{ \n");
                sb.append("\"label\"").append(":\"").append(nav.getOrgName()).append("\",");
                sb.append("\"id\"").append(":").append(nav.getId()).append(",");
                sb.append("\"orgCode\"").append(":\"").append(nav.getOrgCode()).append("\",");
                sb.append("\"parentCode\"").append(":\"").append(nav.getParentCode()).append("\"");
                List<Organization> nav2roots = findByPOrgCode(nav.getOrgCode());
                if (nav2roots.size() != 0) {
                    sb.append(",\"expandedIcon\"").append(":\"").append("fa-folder-open" + "\",");
                    sb.append("\"collapsedIcon\"").append(":\"").append("fa-folder" + "\"");
                    sb.append(",\"children\" :[ \n");
                    sb.append("]\n");
                }

                lastLevelNum = curLevelNum;
                preNav = nav;
            }
            sb.append("} \n");
            for (int j = 1; j < curLevelNum; j++) {
                sb.append("]} \n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("]");
        return sb;
    }


    public static boolean checked(String fzz, String zz) {
        boolean bool = Pattern.matches("^" + fzz + ".*", zz);
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
     * Get all the organizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Organization> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll(pageable);
    }

    /**
     * Get all the organizations.
     *
     * @param orgName the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Organization> findByOrgName(String orgName) {
        log.debug("Request to get all Organization by orgName");
        return organizationRepository.findByOrgName(orgName);
    }

    /**
     * Get one organization by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Organization findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findOne(id);
    }

    /**
     * Delete the  organization by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.delete(id);
    }
}
