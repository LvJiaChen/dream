package com.dream.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/22 13:23
 */
public class WmsDeliverWeekVo {
    private Date deliverDate;

    private String deliverDateStr;

    private BigDecimal money;

    private String week;

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getDeliverDateStr() {
        return deliverDateStr;
    }

    public void setDeliverDateStr(String deliverDateStr) {
        this.deliverDateStr = deliverDateStr;
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
