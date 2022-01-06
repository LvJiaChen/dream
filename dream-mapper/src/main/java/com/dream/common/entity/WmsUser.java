package com.dream.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2022-01-06
 */
@Getter
@Setter
@TableName("wms_user")
@ApiModel(value = "WmsUser对象", description = "")
public class WmsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("工号")
    private String userNo;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;
    @Version
    private Integer version;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;

    private String token;

    @ApiModelProperty("过期时间")
    private LocalDateTime expireTime;

    @ApiModelProperty("登录时间")
    private LocalDateTime loginTime;


}
