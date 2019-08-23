package com.xinzuo.competitive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy年MM月dd日HH时mm分ss秒",timezone = "GMT+8")
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
