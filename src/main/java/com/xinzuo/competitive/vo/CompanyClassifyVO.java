package com.xinzuo.competitive.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 公司分类表
    * </p>
*
* @author jc
* @since 2019-07-09
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class CompanyClassifyVO implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键ID
            */
            @TableId(value = "classify_id", type = IdType.AUTO)
    private Integer classifyId;

            /**
            * 公司分类类型
            */
    private String classifyName;

            /**
            * 排序序号
            */
    private Integer classifySort;
            /**
            * 数量
            */
    private Integer classifyQuantity;


}
