package com.xinzuo.competitive.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class ProjectsVO {
    /**
     * 主键ID
     */
    @TableId
    private String projectsId;

    /**
     * 项目名称
     */

    private String projectsName;

    /**
     * 建设单位
     */

    private String developmentOrganization;

    /**
     * 总参与公司
     */

    private Integer imaginaryQuantity;

    /**
     * 实际参与公司
     */

    private Integer actualQuantity;

    /**
     * 需中选(家)
     */

    private Integer biddingQuantity;

    /**
     * 已抽选(家)
     */

    private Integer winQuantity;


    /**
     * 发包价格
     */

    private BigDecimal outsourcingPrice;

    /**
     * 创建时间
     */
    private Date createTime;

}
