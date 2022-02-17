package com.dream.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-02-17
 */
@Getter
@Setter
@TableName("wms_entry")
@ApiModel(value = "WmsEntry对象", description = "")
public class WmsEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("入库单号")
    private String code;

    @ApiModelProperty("入库批次")
    private String batch;

    @ApiModelProperty("物料编码")
    private String materialNo;

    @ApiModelProperty("物料名称")
    private String materialName;

    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("金额")
    private BigDecimal money;

    @ApiModelProperty("入库时间")
    private Date entryDate;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("状态（入库、冲销）")
    private String status;

    @TableField(fill = FieldFill.INSERT)
    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updater;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


}
