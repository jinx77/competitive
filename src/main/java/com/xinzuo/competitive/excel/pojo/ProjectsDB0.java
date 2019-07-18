package com.xinzuo.competitive.excel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;


/**
* <p>
    * 项目表
    * </p>
*
* @author jc
* @since 2019-06-21
*/
    @Data

    public class ProjectsDB0 extends BaseRowModel implements Serializable {
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
}
