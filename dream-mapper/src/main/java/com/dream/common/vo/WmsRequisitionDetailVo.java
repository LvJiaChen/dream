package com.dream.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lvxiaozuo
 * @date 2022/2/18 16:06
 */
@Data
public class WmsRequisitionDetailVo {

    private String materialNo;
    private String materialName;
    private String brand;
    private String space;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal deliverQuantity;
    private BigDecimal disDeliverQuantity;
    private String status;
    private String unit;
}
