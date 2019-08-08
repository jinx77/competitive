package com.xinzuo.competitive.excel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 企业信息表
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
    @Data
    public class CompanyDB1 extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;
            /**
            * 序号
            */
    @ExcelProperty(index = 0)
    private String number;
            /**
            * 公司名称
            */
    @ExcelProperty(index = 1)
    private String proposerName;

            /**
            * 法定代表人
            */
    @ExcelProperty(index = 2)
    private String legalRepresentative;

            /**
            * 电话
            */
    @ExcelProperty(index = 3)
    private String phone;


}
