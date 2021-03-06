package com.xinzuo.competitive.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
    * 企业信息表
    * </p>
*
* @author jc
* @since 2019-07-09
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class CompanyVO implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键ID
            */
            @TableId
    private String companyId;

            /**
            * 分类主键ID
            */
    private Integer companyClassifyId;
            /**
            * 分类名称
            */
    private String companyClassifyName;

            /**
            * 公司名称
            */
    private String proposerName;

            /**
            * 法定代表人
            */
    private String legalRepresentative;

            /**
            * 电话
            */
    private String phone;

            /**
            * 编号
            */
    private Integer companyNumber;
            /**
            * 创建时间
            */
    private Date createTime;


}
