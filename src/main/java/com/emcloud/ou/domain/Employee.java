package com.emcloud.ou.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 员工表
 * @author daiziying
 */
@ApiModel(description = "员工表 @author daiziying")
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 员工编号
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "员工编号", required = true)
    @Column(name = "ecode", length = 40, nullable = false)
    private String ecode;

    /**
     * 员工姓名
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "员工姓名", required = true)
    @Column(name = "ename", length = 40, nullable = false)
    private String ename;

    /**
     * 组织代码
     */
    @NotNull
    @Size(max = 46)
    @ApiModelProperty(value = "组织代码", required = true)
    @Column(name = "org_code", length = 46, nullable = false)
    private String orgCode;

    /**
     * 公司代码
     */
    @NotNull
    @Size(max = 46)
    @ApiModelProperty(value = "公司代码", required = true)
    @Column(name = "company_code", length = 46, nullable = false)
    private String companyCode;

    /**
     * 职位
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "职位", required = true)
    @Column(name = "position", length = 100, nullable = false)
    private String position;

    /**
     * 性别
     */
    @NotNull
    @Size(max = 2)
    @ApiModelProperty(value = "性别", required = true)
    @Column(name = "sex", length = 2, nullable = false)
    private String sex;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    @Column(name = "birth_date")
    private Instant birthDate;

    /**
     * 身份证号
     */
    @Size(max = 20)
    @ApiModelProperty(value = "身份证号")
    @Column(name = "id_code", length = 20)
    private String idCode;

    /**
     * 社保账号
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "社保账号", required = true)
    @Column(name = "insurcode", length = 40, nullable = false)
    private String insurcode;

    /**
     * 家庭地址
     */
    @Size(max = 200)
    @ApiModelProperty(value = "家庭地址")
    @Column(name = "home_address", length = 200)
    private String homeAddress;

    /**
     * 手机号码
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "手机号码", required = true)
    @Column(name = "mobile", length = 20, nullable = false)
    private String mobile;

    /**
     * 邮箱
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "邮箱", required = true)
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    /**
     * 入职日期
     */
    @NotNull
    @ApiModelProperty(value = "入职日期", required = true)
    @Column(name = "join_date", nullable = false)
    private Instant joinDate;

    /**
     * 是否有效
     */
    @NotNull
    @ApiModelProperty(value = "是否有效", required = true)
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    /**
     * 附件数
     */
    @ApiModelProperty(value = "附件数")
    @Column(name = "attachs_num")
    private Integer attachsNum;

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

    public Employee ecode(String ecode) {
        this.ecode = ecode;
        return this;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEname() {
        return ename;
    }

    public Employee ename(String ename) {
        this.ename = ename;
        return this;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public Employee orgCode(String orgCode) {
        this.orgCode = orgCode;
        return this;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public Employee companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getPosition() {
        return position;
    }

    public Employee position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSex() {
        return sex;
    }

    public Employee sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public Employee birthDate(Instant birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdCode() {
        return idCode;
    }

    public Employee idCode(String idCode) {
        this.idCode = idCode;
        return this;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getinsurcode() {
        return insurcode;
    }

    public Employee insurcode(String insurcode) {
        this.insurcode = insurcode;
        return this;
    }

    public void setinsurcode(String insurcode) {
        this.insurcode = insurcode;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public Employee homeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
        return this;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public Employee mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getJoinDate() {
        return joinDate;
    }

    public Employee joinDate(Instant joinDate) {
        this.joinDate = joinDate;
        return this;
    }

    public void setJoinDate(Instant joinDate) {
        this.joinDate = joinDate;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Employee enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getAttachsNum() {
        return attachsNum;
    }

    public Employee attachsNum(Integer attachsNum) {
        this.attachsNum = attachsNum;
        return this;
    }

    public void setAttachsNum(Integer attachsNum) {
        this.attachsNum = attachsNum;
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
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", ecode='" + getEcode() + "'" +
            ", ename='" + getEname() + "'" +
            ", orgCode='" + getOrgCode() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", position='" + getPosition() + "'" +
            ", sex='" + getSex() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", idCode='" + getIdCode() + "'" +
            ", insurcode='" + getinsurcode() + "'" +
            ", homeAddress='" + getHomeAddress() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", joinDate='" + getJoinDate() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", attachsNum='" + getAttachsNum() + "'" +
            "}";
    }
}
