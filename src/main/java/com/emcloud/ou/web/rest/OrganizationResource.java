package com.emcloud.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.ou.domain.Organization;
import com.emcloud.ou.repository.OrganizationRepository;
import com.emcloud.ou.service.OrganizationService;
import com.emcloud.ou.web.rest.errors.BadRequestAlertException;
import com.emcloud.ou.web.rest.util.HeaderUtil;
import com.emcloud.ou.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing Organization.
 */
@RestController
@RequestMapping("/api")
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    private static final String ENTITY_NAME = "organization";

    private final OrganizationService organizationService;

    public OrganizationResource(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/roots-organizations")
    @Timed
    public StringBuilder getRoots() {

        int lastLevelNum = 0; // 上一次的层次
        int curLevelNum = 0; // 本次对象的层次

        List<Organization> roots = organizationService.findByPOrgCode("0");
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
                List<Organization> nav2roots = organizationService.findByPOrgCode(nav.getOrgCode());
                if (nav2roots.size() != 0) {
                    sb.append(",\"leaf\"").append(":").append(false);
                    sb.append(",\"expandedIcon\"").append(":\"").append("fa-folder-open" + "\",");
                    sb.append("\"collapsedIcon\"").append(":\"").append("fa-folder" + "\"");
                    sb.append(",\"children\" :[ \n");
                    sb.append("] \n");
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

    @GetMapping("/all-organizations")
    @Timed
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        List<Organization> list = organizationService.findAll();

        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/organizations/by-org-code/{orgCode}")
    public ResponseEntity<List<Organization>> getAllByOrgCode
        (@PathVariable(value = "orgCode") String orgCode) {

        List<Organization> list = organizationService.findByOrgCode(orgCode);
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/organizations/by-company-code/{companyCode}")
    public ResponseEntity<List<Organization>> getAllByCompanyCode
        (@PathVariable String companyCode) {

        List<Organization> list = organizationService.findAllByCompanyCode(companyCode);
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/organizations/by-parent-org-code")
    public ResponseEntity<List<Organization>> getAllByParentOrgCode
        (@RequestParam(value = "parentOrgCode") String parentOrgCode) {

        List<Organization> list = organizationService.findByPOrgCode(parentOrgCode);
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * POST  /organizations : Create a new organization.
     *
     * @param organization the organization to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organization, or with status 400 (Bad Request) if the organization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organizations")
    @Timed
    public ResponseEntity<Organization> createOrganization(@Valid @RequestBody Organization organization) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organization);
        if (organization.getId() != null) {
            throw new BadRequestAlertException("A new organization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Organization result = organizationService.save(organization);
        return ResponseEntity.created(new URI("/api/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizations : Updates an existing organization.
     *
     * @param organization the organization to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organization,
     * or with status 400 (Bad Request) if the organization is not valid,
     * or with status 500 (Internal Server Error) if the organization couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organizations")
    @Timed
    public ResponseEntity<Organization> updateOrganization(@Valid @RequestBody Organization organization) throws URISyntaxException {
        log.debug("REST request to update Organization : {}", organization);
        if (organization.getId() == null) {
            return createOrganization(organization);
        }
        Organization result = organizationService.update(organization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organization.getId().toString()))
            .body(result);
    }


    //树形
    @GetMapping("/organizations/tree")
    public StringBuilder findTree() {
        int lastLevelNum = 0; // 上一次的层次
        int curLevelNum = 0; // 本次对象的层次
        // Map<String, Object> data = new HashMap<String, Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        try {//查询所有菜单
            List<Organization> allMenu = organizationService.findAll();
            Collections.sort(allMenu, new Comparator<Organization>() {
                @Override
                public int compare(Organization o1, Organization o2) {
                    return o1.getOrgCode().compareTo(o2.getOrgCode());
                }
            });
            Organization preNav = null;
            for (Organization nav : allMenu) {
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
                    } else {
                        sb.append(",\"expandedIcon\"").append(":\"").append("fa-folder-open" + "\",");
                        sb.append("\"collapsedIcon\"").append(":\"").append("fa-folder" + "\"");
                        sb.append(",\"children\" :[ \n");
                    }
                }
                sb.append("{ \n");
                sb.append("\"label\"").append(":\"").append(nav.getOrgName()).append("\",");
                sb.append("\"id\"").append(":").append(nav.getId()).append(",");
                sb.append("\"orgCode\"").append(":\"").append(nav.getOrgCode()).append("\",");
                sb.append("\"parentCode\"").append(":\"").append(nav.getParentCode()).append("\"");
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

    private static int getLevelNum(Organization org) {
        return org.getOrgCode().length() / 2;
    }

    /**
     * GET  /organizations : get all the organizations.
     *
     * @param orgName the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organizations in body
     */
    @GetMapping("/organizations/by-org-name")
    public ResponseEntity<List<Organization>> getAllByOrgName
    (@RequestParam(value = "orgName") String orgName) {
        log.debug("REST orgName to get a page of Organization");
        List<Organization> list = organizationService.findByOrgName(orgName);
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/organizations/by-company-name/{companyName}")
    public ResponseEntity<List<Organization>> getAllByCompanyName
        (@PathVariable String companyName) {
        log.debug("REST companyName to get a page of Organization");

        List<Organization> list = organizationService.findAllByCompanyName(companyName);
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET  /organizations : get all the organizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organizations in body
     */
    @GetMapping("/organizations")
    @Timed
    public ResponseEntity<List<Organization>> getAllOrganizations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Organizations");
        Page<Organization> page = organizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organizations/:id : get the "id" organization.
     *
     * @param id the id of the organization to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organization, or with status 404 (Not Found)
     */
    @GetMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<Organization> getOrganization(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        Organization organization = organizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organization));
    }


    /**
     * DELETE  /organizations/:id : delete the "id" organization.
     *
     * @param id the id of the organization to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
