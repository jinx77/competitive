package com.xinzuo.competitive.pojo;

    import java.io.Serializable;

    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 用户表
    * </p>
*
* @author jc
* @since 2019-06-21
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 用户主键ID
            */
            @TableId
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
            * 初始密码
            */
    private String initialPassword;
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
