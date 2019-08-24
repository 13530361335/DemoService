package com.joker.sql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author: Joker Jing
 * @date: 2019/7/29
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class User {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(update = "now()")
    private Date updateTime;

    public User(String account, String password, String realName, String telephone, String email) {
        this.account = account;
        this.password = password;
        this.realName = realName;
        this.telephone = telephone;
        this.email = email;
        Date date = new Date();
        this.createTime = date;
        this.updateTime = date;
    }

}