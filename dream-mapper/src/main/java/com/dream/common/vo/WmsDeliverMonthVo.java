package com.dream.common.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/22 15:23
 */
@Data
public class WmsDeliverMonthVo {
    private Date deliverDate;

    private String deliverDateStr;

    private BigDecimal money;

    private String month;

}
