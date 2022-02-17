package com.dream.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/17 11:24
 */
public class WmsEntryPageVo {
    private String code;
    private Date entryDate;
    private String warehouseCode;
    private String warehouseName;
    private BigDecimal moneyAll;
    private String creator;
    private Date createTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
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
