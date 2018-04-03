package com.emcloud.ou.repository;

import com.emcloud.ou.domain.UserEmp;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserEmp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserEmpRepository extends JpaRepository<UserEmp, Long> {

    UserEmp findOneByLogin(String login);
}
