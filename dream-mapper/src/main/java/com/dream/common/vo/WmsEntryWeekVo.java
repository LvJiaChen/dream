package com.dream.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/22 13:22
 */
public class WmsEntryWeekVo {
    private Date entryDate;

    private String entryDateStr;

    private BigDecimal money;

    private String week;

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryDateStr() {
        return entryDateStr;
    }

    public void setEntryDateStr(String entryDateStr) {
        this.entryDateStr = entryDateStr;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
