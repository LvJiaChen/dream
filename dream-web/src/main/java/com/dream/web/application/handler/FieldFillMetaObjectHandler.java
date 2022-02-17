package com.dream.web.application.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dream.common.vo.UserInfo;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lvxiaozuo
 * @date 2022/1/16 14:20
 */
@Component
public class FieldFillMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.setFieldValByName("creator", UserInfo.getUser().getUserNo(), metaObject);
        this.setFieldValByName("version",1,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updater", UserInfo.getUser().getUserNo(), metaObject);
    }
}
