package com.xinzuo.competitive.excel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import java.io.Serializable;

/**
* <p>
    * 承包商信息库表
    * </p>
*
* @author jc
* @since 2019-06-22
*/
    @Data
    public class InformationDB extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;
   /*         *//**
            * 序号
            *//*
    @ExcelProperty(index = 1)
    private String proposerName;*/
            /**
            * 申请人名称
            */
            @ExcelProperty(index = 0)
    private String proposerName;

            /**
            * 法定代表人
            */
            @ExcelProperty(index = 1)
    private String legalRepresentative;

            /**
            * 联系电话
            */
            @ExcelProperty(index = 2)
    private String phone;


}
