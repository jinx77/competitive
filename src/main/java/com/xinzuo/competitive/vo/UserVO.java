package com.xinzuo.competitive.vo;

import lombok.Data;

@Data
public class UserVO {

    /**
     * 用户主键ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;
    /**
     * logo图标title
     */
    private String logoIcon;
    /**
     *标题
     */
    private String titleName;
    /**
     *角色  0超级管理员 1普通操作员
     */
    private int role;
}
