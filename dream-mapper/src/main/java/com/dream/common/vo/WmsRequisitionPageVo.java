package com.dream.common.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/2/18 16:06
 */
@Data
public class WmsRequisitionPageVo {
    private String code;
    private Date requisitionDate;
    private String materialNameConcat;
    private String creator;
    private Date createTime;
    private String status;
}
