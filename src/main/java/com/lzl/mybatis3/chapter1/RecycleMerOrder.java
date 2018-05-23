package com.lzl.mybatis3.chapter1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RecycleMerOrder implements Serializable {
    private static final long serialVersionUID = 8094294486774948808L;
    private Long recycleOrderId;

    private Long merId;

    private String classid;

    private BigDecimal price;

    private BigDecimal shouldPayPrice;

    private BigDecimal realPayPrice;

    private Integer num;

    private Integer status;

    private Integer lockNum;

    private Integer remainNum;

    private Long payOrderId;

    private Date payTime;

    private Integer version;

    private Date createTime;

    private Date updateTime;

    public Long getRecycleOrderId() {
        return recycleOrderId;
    }

    public void setRecycleOrderId(Long recycleOrderId) {
        this.recycleOrderId = recycleOrderId;
    }

    public Long getMerId() {
        return merId;
    }

    public void setMerId(Long merId) {
        this.merId = merId;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid == null ? null : classid.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getShouldPayPrice() {
        return shouldPayPrice;
    }

    public void setShouldPayPrice(BigDecimal shouldPayPrice) {
        this.shouldPayPrice = shouldPayPrice;
    }

    public BigDecimal getRealPayPrice() {
        return realPayPrice;
    }

    public void setRealPayPrice(BigDecimal realPayPrice) {
        this.realPayPrice = realPayPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLockNum() {
        return lockNum;
    }

    public void setLockNum(Integer lockNum) {
        this.lockNum = lockNum;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}