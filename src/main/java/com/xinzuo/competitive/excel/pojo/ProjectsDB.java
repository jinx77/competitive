package com.xinzuo.competitive.excel.pojo;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;


/**
* <p>
    * 项目表
    * </p>
*
* @author jc
* @since 2019-06-21
*/
    @Data

    public class ProjectsDB extends BaseRowModel implements Serializable {
            /**
            * 项目名称
            */
            @ExcelProperty(index = 1)
    private String projectsName;

            /**
            * 建设单位
            */
            @ExcelProperty(index = 2)
    private String developmentOrganization;
            /**
            * 发包价格
            */
            @ExcelProperty(index = 3)
    private BigDecimal outsourcingPrice;
            /**
            * 需中选(家)
            */
            @ExcelProperty(index = 4)
    private Integer biddingQuantity;

}
