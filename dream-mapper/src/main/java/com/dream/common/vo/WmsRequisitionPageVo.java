package com.dream.common.vo;

import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/18 16:06
 */
public class WmsRequisitionPageVo {
    private String code;
    private Date requisitionDate;
    private String materialNameConcat;
    private String creator;
    private Date createTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(Date requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public String getMaterialNameConcat() {
        return materialNameConcat;
    }

    public void setMaterialNameConcat(String materialNameConcat) {
        this.materialNameConcat = materialNameConcat;
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
