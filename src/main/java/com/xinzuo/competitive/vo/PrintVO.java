package com.xinzuo.competitive.vo;

import lombok.Data;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PrintVO {

    /**
     * 项目名称
     */

    private String projectsName;

    /**
     * 建设单位
     */

    private String developmentOrganization;

    /**
     * 抽取时间
     */
    private Date winTime;

    /**
     * 发包价格
     */
    private BigDecimal outsourcingPrice;
    /**
     * 中选企业
     */
    private List<WinVO> winVOList;

}
