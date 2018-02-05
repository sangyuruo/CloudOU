package com.emcloud.ou.repository;

import com.emcloud.ou.domain.Organization;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Organization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findAllByCompanyName(String companyName);
    List<Organization> findByOrgName(String orgName);


    /**查所有 */
    List<Organization> findAll();
    /**根据公司code查所有 */
    List<Organization> findAllByCompanyCode(String companyCode);
    /**根据组织code查所有 */
    List<Organization> findAllByOrgCode(String orgCode);

}
