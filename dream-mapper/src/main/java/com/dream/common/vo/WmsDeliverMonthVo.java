package com.dream.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/22 15:23
 */
public class WmsDeliverMonthVo {
    private Date deliverDate;

    private String deliverDateStr;

    private BigDecimal money;

    private String month;

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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
