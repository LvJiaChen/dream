package com.dream.common.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/17 11:24
 */
@Data
public class WmsEntryPageVo {
    private String code;
    private Date entryDate;
    private String warehouseCode;
    private String materialNameConcat;
    private String warehouseName;
    private BigDecimal moneyAll;
    private String creator;
    private Date createTime;

}
