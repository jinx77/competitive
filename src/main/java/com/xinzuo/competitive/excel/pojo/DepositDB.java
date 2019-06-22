package com.xinzuo.competitive.excel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
    * 保证金表
    * </p>
*
* @author jc
* @since 2019-06-21
*/
    @Data
    public class DepositDB extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 来款户名
            */
            @ExcelProperty(index = 1)
    private String depositName;

            /**
            * 来款账号
            */
            @ExcelProperty(index = 2)
    private String depositAccount;

            /**
            * 来款银行
            */
            @ExcelProperty(index = 3)
    private String depositBank;

           /**
            * 到账时间
            */
            @ExcelProperty(index = 4)
    private String intoTime;

            /**
            * 汇款途径
            */
            @ExcelProperty(index = 5)
    private String remitPathway;

            /**
            * 保证金额度
            */
            @ExcelProperty(index = 6)
    private BigDecimal depositMoney;
}
