package com.xinzuo.competitive.form;

import lombok.Data;

/**
 * PageForm:默认分页大小
 * @author zhangxiaoxiang
 * @date 2019/6/12
 */
@Data
public class PageForm {
    /**
     * 当前页
     */
    private Integer current=1;
    /**
     * 每页记录数
     */
    private Integer size=10;
    /**
     * 每页记录数
     */
    private String qualificationId;
    /**
     * 查询条件
     */
    private String condition;
    /**
     * 查询条件 1全部，2未缴纳保证金，3未上传资料库，4可以参与的公司
     */
    private Integer selectType;
}
