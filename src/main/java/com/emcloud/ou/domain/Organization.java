package com.emcloud.ou.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * 组织架构表
 * @author daiziying
 */
@ApiModel(description = "组织架构表 @author daiziying")
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 组织代码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "组织代码", required = true)
    @Column(name = "org_code", length = 64, nullable = false)
    private String orgCode;

    /**
     * 组织名称
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "组织名称", required = true)
    @Column(name = "org_name", length = 40, nullable = false)
    private String orgName;

    /**
     * 01-业务部门 02-组织单元
     */
    @Size(max = 100)
    @ApiModelProperty(value = "01-业务部门 02-组织单元")
    @Column(name = "org_type", length = 100)
    private String orgType;

    /**
     * 公司代码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "公司代码", required = true)
    @Column(name = "company_code", length = 64, nullable = false)
    private String companyCode;

    /**
     * 上级代码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "上级代码")
    @Column(name = "parent_code", length = 64)
    private String parentCode;

    /**
     * 电话
     */
    @Size(max = 100)
    @ApiModelProperty(value = "电话")
    @Column(name = "telephone", length = 100)
    private String telephone;

    /**
     * 公司名
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "公司名", required = true)
    @Column(name = "company_name", length = 40, nullable = false)
    private String companyName;

    /**
     * 父组织名
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "父组织名", required = true)
    @Column(name = "parent_org_name", length = 40, nullable = false)
    private String parentOrgName;

    /**
     * 地址名称
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "地址名称", required = true)
    @Column(name = "address_name", length = 40, nullable = false)
    private String addressName;

    /**
     * 地址代码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "地址代码")
    @Column(name = "address_code", length = 64)
    private String addressCode;

    /**
     * 备注
     */
    @Size(max = 500)
    @ApiModelProperty(value = "备注")
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    @Column(name = "seq_no")
    private Integer seqNo;

    /**
     * 附件数
     */
    @ApiModelProperty(value = "附件数")
    @Column(name = "attachs_num")
    private Integer attachsNum;

    /**
     * 是否可用
     */
    @NotNull
    @ApiModelProperty(value = "是否可用", required = true)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Company company;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public Organization orgCode(String orgCode) {
        this.orgCode = orgCode;
        return this;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public Organization orgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public Organization orgType(String orgType) {
        this.orgType = orgType;
        return this;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public Organization companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public Organization parentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public Organization telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Organization companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentOrgName() {
        return parentOrgName;
    }

    public Organization parentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
        return this;
    }

    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }

    public String getAddressName() {
        return addressName;
    }

    public Organization addressName(String addressName) {
        this.addressName = addressName;
        return this;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public Organization addressCode(String addressCode) {
        this.addressCode = addressCode;
        return this;
    }
    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getRemark() {
        return remark;
    }

    public Organization remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public Organization seqNo(Integer seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getAttachsNum() {
        return attachsNum;
    }

    public Organization attachsNum(Integer attachsNum) {
        this.attachsNum = attachsNum;
        return this;
    }

    public void setAttachsNum(Integer attachsNum) {
        this.attachsNum = attachsNum;
    }

    public Boolean getEnable() {
        return enable;
    }

    public Organization enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Organization createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Organization createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Organization updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Organization updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Company getCompany() {
        return company;
    }

    public Organization company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        Organization organization = (Organization) o;
        if (organization.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", orgCode='" + getOrgCode() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", orgType='" + getOrgType() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", parentCode='" + getParentCode() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", parentOrgName='" + getParentOrgName() + "'" +
            ", addressName='" + getAddressName() + "'" +
            ", addressCode='" + getAddressCode() + "'" +
            ", remark='" + getRemark() + "'" +
            ", seqNo='" + getSeqNo() + "'" +
            ", attachsNum='" + getAttachsNum() + "'" +
            ", enable='" + getEnable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }

}
