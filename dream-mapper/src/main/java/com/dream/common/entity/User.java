package com.dream.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author lvxiaozuo
 * @since 2021-11-28
 */
@TableName("dream_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer age;

    private String email;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;

    private String uuidT;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getUuidT() {
        return uuidT;
    }

    public void setUuidT(String uuidT) {
        this.uuidT = uuidT;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name=" + name +
            ", age=" + age +
            ", email=" + email +
            ", creator=" + creator +
            ", createTime=" + createTime +
            ", updater=" + updater +
            ", updateTime=" + updateTime +
            ", uuidT=" + uuidT +
        "}";
    }
}
