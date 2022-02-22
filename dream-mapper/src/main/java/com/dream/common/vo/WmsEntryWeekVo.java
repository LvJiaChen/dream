package com.dream.common.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/22 13:22
 */
@Data
public class WmsEntryWeekVo {
    private Date entryDate;

    private String entryDateStr;

    private BigDecimal money;

    private String week;

}
