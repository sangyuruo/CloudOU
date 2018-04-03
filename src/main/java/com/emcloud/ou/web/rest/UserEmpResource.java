package com.emcloud.ou.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.ou.domain.UserEmp;
import com.emcloud.ou.service.UserEmpService;
import com.emcloud.ou.web.rest.errors.BadRequestAlertException;
import com.emcloud.ou.web.rest.util.HeaderUtil;
import com.emcloud.ou.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserEmp.
 */
@RestController
@RequestMapping("/api")
public class UserEmpResource {

    private final Logger log = LoggerFactory.getLogger(UserEmpResource.class);

    private static final String ENTITY_NAME = "userEmp";

    private final UserEmpService userEmpService;

    public UserEmpResource(UserEmpService userEmpService) {
        this.userEmpService = userEmpService;
    }

    /**
     * POST  /user-emps : Create a new userEmp.
     *
     * @param userEmp the userEmp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userEmp, or with status 400 (Bad Request) if the userEmp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-emps")
    @Timed
    public ResponseEntity<UserEmp> createUserEmp(@Valid @RequestBody UserEmp userEmp) throws URISyntaxException {
        log.debug("REST request to save UserEmp : {}", userEmp);
        if (userEmp.getId() != null) {
            throw new BadRequestAlertException("A new userEmp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserEmp result = userEmpService.save(userEmp);
        return ResponseEntity.created(new URI("/api/user-emps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-emps : Updates an existing userEmp.
     *
     * @param userEmp the userEmp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userEmp,
     * or with status 400 (Bad Request) if the userEmp is not valid,
     * or with status 500 (Internal Server Error) if the userEmp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-emps")
    @Timed
    public ResponseEntity<UserEmp> updateUserEmp(@Valid @RequestBody UserEmp userEmp) throws URISyntaxException {
        log.debug("REST request to update UserEmp : {}", userEmp);
        if (userEmp.getId() == null) {
            return createUserEmp(userEmp);
        }
        UserEmp result = userEmpService.save(userEmp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userEmp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-emps : get all the userEmps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userEmps in body
     */
    @GetMapping("/user-emps")
    @Timed
    public ResponseEntity<List<UserEmp>> getAllUserEmps(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserEmps");
        Page<UserEmp> page = userEmpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-emps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-emps/:id : get the "id" userEmp.
     *
     * @param id the id of the userEmp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userEmp, or with status 404 (Not Found)
     */
    @GetMapping("/user-emps/{id}")
    @Timed
    public ResponseEntity<UserEmp> getUserEmp(@PathVariable Long id) {
        log.debug("REST request to get UserEmp : {}", id);
        UserEmp userEmp = userEmpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userEmp));
    }

    /**
     * DELETE  /user-emps/:id : delete the "id" userEmp.
     *
     * @param id the id of the userEmp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-emps/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserEmp(@PathVariable Long id) {
        log.debug("REST request to delete UserEmp : {}", id);
        userEmpService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /user-emps/:login : get the "login" userEmp.
     *
     * @param login the id of the userEmp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userEmp, or with status 404 (Not Found)
     */
    @GetMapping("/user-emps/{login}")
    @Timed
    public ResponseEntity<UserEmp> getUserEmpByLogin(@PathVariable String login) {
        log.debug("REST request to get UserEmp : {}", login);
        UserEmp userEmp = userEmpService.findOneByLogin(login);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userEmp));
    }
}
