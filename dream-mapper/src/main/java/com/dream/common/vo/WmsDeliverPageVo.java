package com.dream.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/17 11:24
 */
public class WmsDeliverPageVo {
    private String code;
    private Date deliverDate;
    private String referenceNo;
    private String warehouseCode;
    private String materialNameConcat;
    private String warehouseName;
    private BigDecimal moneyAll;
    private String creator;
    private Date createTime;

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getMaterialNameConcat() {
        return materialNameConcat;
    }

    public void setMaterialNameConcat(String materialNameConcat) {
        this.materialNameConcat = materialNameConcat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public BigDecimal getMoneyAll() {
        return moneyAll;
    }

    public void setMoneyAll(BigDecimal moneyAll) {
        this.moneyAll = moneyAll;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
