package com.xinzuo.competitive.form;

import lombok.Data;

@Data
public class UserForm {

    /**
     * 用户主键ID
     */
    private String userId;

    /**
     * 新密码
     */
    private String userPassword;

    /**
     * 原始密码
     */
    private String initialPassword;

    /**
     * 登录logo图标
     */
    private String logoIcon;

    /**
     * 系统标题
     */
    private String titleName;

}
