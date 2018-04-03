package com.emcloud.ou.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 用户员工关系表
 * @author daiziying
 */
@ApiModel(description = "用户员工关系表 @author daiziying")
@Entity
@Table(name = "user_emp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserEmp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "ecode", length = 40, nullable = false)
    private String ecode;

    @NotNull
    @Size(max = 50)
    @Column(name = "login", length = 50, nullable = false)
    private String login;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEcode() {
        return ecode;
    }

    public UserEmp ecode(String ecode) {
        this.ecode = ecode;
        return this;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getLogin() {
        return login;
    }

    public UserEmp login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEmp userEmp = (UserEmp) o;
        if (userEmp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userEmp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserEmp{" +
            "id=" + getId() +
            ", ecode='" + getEcode() + "'" +
            ", login='" + getLogin() + "'" +
            "}";
    }
}
