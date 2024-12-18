package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 床位信息对象 sys_bed
 */
public class SysBed extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 床位ID */
    private Long bedId;

    /** 床位号 */
    @Excel(name = "床位号")
    private String bedNumber;

    /** 入住老人ID */
    @Excel(name = "入住老人ID")
    private Long elderId;

    /** 床位价格 */
    @Excel(name = "床位价格")
    private BigDecimal price;

    /** 床位状态（0空闲 1已入住 2维护） */
    @Excel(name = "床位状态", readConverterExp = "0=空闲,1=已入住,2=维护")
    private String status;

    /** 备注 */
    private String remark;

    /** 老人姓名 */
    @Excel(name = "入住老人")
    private String elderName;

    /** 老人电话 */
    private String elderPhone;

    /** 老人性别 */
    private String elderGender;

    /** 老人年龄 */
    private Long elderAge;

    public void setBedId(Long bedId) 
    {
        this.bedId = bedId;
    }

    public Long getBedId() 
    {
        return bedId;
    }

    public void setBedNumber(String bedNumber) 
    {
        this.bedNumber = bedNumber;
    }

    public String getBedNumber() 
    {
        return bedNumber;
    }

    public void setElderId(Long elderId) 
    {
        this.elderId = elderId;
    }

    public Long getElderId() 
    {
        return elderId;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setRemark(String remark) 
    {
        this.remark = remark;
    }

    public String getRemark() 
    {
        return remark;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public String getElderPhone() {
        return elderPhone;
    }

    public void setElderPhone(String elderPhone) {
        this.elderPhone = elderPhone;
    }

    public String getElderGender() {
        return elderGender;
    }

    public void setElderGender(String elderGender) {
        this.elderGender = elderGender;
    }

    public Long getElderAge() {
        return elderAge;
    }

    public void setElderAge(Long elderAge) {
        this.elderAge = elderAge;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bedId", getBedId())
            .append("bedNumber", getBedNumber())
            .append("elderId", getElderId())
            .append("price", getPrice())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("elderName", getElderName())
            .append("elderPhone", getElderPhone())
            .append("elderGender", getElderGender())
            .append("elderAge", getElderAge())
            .toString();
    }
} 