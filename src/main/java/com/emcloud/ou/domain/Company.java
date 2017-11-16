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
 * 公司表
 * @author daiziying
 */
@ApiModel(description = "公司表 @author daiziying")
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公司全名
     */
    @Size(max = 200)
    @ApiModelProperty(value = "公司全名")
    @Column(name = "company_long_name", length = 200)
    private String companyLongName;

    /**
     * 公司名
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "公司名", required = true)
    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    /**
     * 父公司名
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "父公司名", required = true)
    @Column(name = "parent_company_name", length = 100, nullable = false)
    private String parentCompanyName;

    /**
     * 公司代码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "公司代码", required = true)
    @Column(name = "company_code", length = 64, nullable = false)
    private String companyCode;

    /**
     * 国家代码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "国家代码", required = true)
    @Column(name = "country_code", length = 64, nullable = false)
    private String countryCode;

    /**
     * 城市代码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "城市代码", required = true)
    @Column(name = "city_code", length = 64, nullable = false)
    private String cityCode;

    /**
     * 地址代码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "地址代码")
    @Column(name = "address_code", length = 64)
    private String addressCode;

    /**
     * 地址名
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "地址名", required = true)
    @Column(name = "address_name", length = 100, nullable = false)
    private String addressName;

    /**
     * 电话号码
     */
    @Size(max = 20)
    @ApiModelProperty(value = "电话号码")
    @Column(name = "telephone", length = 20)
    private String telephone;

    /**
     * 法人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "法人")
    @Column(name = "legal_person", length = 20)
    private String legalPerson;

    /**
     * 父公司代码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "父公司代码")
    @Column(name = "parent_company_code", length = 64)
    private String parentCompanyCode;

    /**
     * 级别id
     */
    @ApiModelProperty(value = "级别id")
    @Column(name = "level_id")
    private Integer levelId;

    /**
     * 备注
     */
    @Size(max = 500)
    @ApiModelProperty(value = "备注")
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 附件数
     */
    @ApiModelProperty(value = "附件数")
    @Column(name = "attachs_num")
    private Integer attachsNum;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @Column(name = "jhi_sort")
    private Integer sort;

    /**
     * 是否可用
     */
    @NotNull
    @ApiModelProperty(value = "是否可用", required = true)
    @Column(name = "jhi_enable", nullable = false)
    private Integer enable;

    /**
     * 创建人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "创建人", required = true)
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 修改人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "修改人", required = true)
    @Column(name = "updated_by", length = 20, nullable = false)
    private String updatedBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = true)
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyLongName() {
        return companyLongName;
    }

    public Company companyLongName(String companyLongName) {
        this.companyLongName = companyLongName;
        return this;
    }

    public void setCompanyLongName(String companyLongName) {
        this.companyLongName = companyLongName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public Company parentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
        return this;
    }

    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public Company companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Company countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public Company cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public Company addressCode(String addressCode) {
        this.addressCode = addressCode;
        return this;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressName() {
        return addressName;
    }

    public Company addressName(String addressName) {
        this.addressName = addressName;
        return this;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getTelephone() {
        return telephone;
    }

    public Company telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public Company legalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
        return this;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getParentCompanyCode() {
        return parentCompanyCode;
    }

    public Company parentCompanyCode(String parentCompanyCode) {
        this.parentCompanyCode = parentCompanyCode;
        return this;
    }

    public void setParentCompanyCode(String parentCompanyCode) {
        this.parentCompanyCode = parentCompanyCode;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public Company levelId(Integer levelId) {
        this.levelId = levelId;
        return this;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getRemark() {
        return remark;
    }

    public Company remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAttachsNum() {
        return attachsNum;
    }

    public Company attachsNum(Integer attachsNum) {
        this.attachsNum = attachsNum;
        return this;
    }

    public void setAttachsNum(Integer attachsNum) {
        this.attachsNum = attachsNum;
    }

    public Integer getSort() {
        return sort;
    }

    public Company sort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnable() {
        return enable;
    }

    public Company enable(Integer enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Company createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Company createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Company updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Company updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyLongName='" + getCompanyLongName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", parentCompanyName='" + getParentCompanyName() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", cityCode='" + getCityCode() + "'" +
            ", addressCode='" + getAddressCode() + "'" +
            ", addressName='" + getAddressName() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", legalPerson='" + getLegalPerson() + "'" +
            ", parentCompanyCode='" + getParentCompanyCode() + "'" +
            ", levelId='" + getLevelId() + "'" +
            ", remark='" + getRemark() + "'" +
            ", attachsNum='" + getAttachsNum() + "'" +
            ", sort='" + getSort() + "'" +
            ", enable='" + getEnable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
