package com.dream.common.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/1/6 15:56
 */
@Data
public class TokenVO {
    private String token;
    private Date expireTime;
}
