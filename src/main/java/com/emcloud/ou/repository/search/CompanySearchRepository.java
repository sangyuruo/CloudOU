package com.emcloud.ou.repository.search;

import com.emcloud.ou.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanySearchRepository extends ElasticsearchRepository<Company, Long> {
    /**
     * 根据公司名或地址名查
     */
    Page<Company> findByCompanyNameContainingOrAddressNameContaining(Pageable pageable, String companyName, String addressName);

    @Override
    List<Company> findAll();

    @Override
    Page<Company> findAll(Pageable pageable);
}
