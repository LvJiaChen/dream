package com.dream.common.vo;

import java.math.BigDecimal;

/**
 * @author lvxiaozuo
 * @date 2022/2/18 16:06
 */
public class WmsRequisitionDetailVo {

    private String materialNo;
    private String materialName;
    private String brand;
    private String space;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal deliverQuantity;
    private String status;
    private String unit;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getDeliverQuantity() {
        return deliverQuantity;
    }

    public void setDeliverQuantity(BigDecimal deliverQuantity) {
        this.deliverQuantity = deliverQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }
}
