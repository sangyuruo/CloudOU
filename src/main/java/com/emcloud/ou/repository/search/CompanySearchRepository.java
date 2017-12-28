package com.emcloud.ou.repository.search;

import com.emcloud.ou.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanySearchRepository extends //ElasticsearchTemplate<Company>,
 ElasticsearchRepository<Company, Long>{
    /**
     * 根据公司名或地址名查
     */
   // Page<Company> findByCompanyNameContainingOrAddressNameContaining(Pageable pageable, String companyName, String addressName);

    @Override
    List<Company> findAll();

    @Override
    Page<Company> findAll(Pageable pageable);


//    Page<Companyny> findByCompanyNameContainingOrAddressNameContainingOrParentCompanyNameContainingOrCompanyCodeContainingOrCityCodeContainingOrCountryCodeContaining
//        (Pageable pageable, String companyName, String addressName,String parentCompanyName,String companyCode,String countryCode,String cityCode);

    @Query("{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"_all\",\"query\":\"*?0*\"}}]}}}")
    Page<Company> findByAll(String query,Pageable pageable);
}
