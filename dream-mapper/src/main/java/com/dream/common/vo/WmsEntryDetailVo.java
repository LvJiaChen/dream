package com.dream.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lvxiaozuo
 * @date 2022/2/18 14:32
 */
@Data
public class WmsEntryDetailVo {
    private String materialNo;
    private String materialName;
    private String brand;
    private String space;
    private BigDecimal price;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal money;

}
