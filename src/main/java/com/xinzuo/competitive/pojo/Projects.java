package com.xinzuo.competitive.pojo;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import java.util.Date;

    import com.baomidou.mybatisplus.annotation.TableId;
    import com.fasterxml.jackson.annotation.JsonInclude;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 项目表
    * </p>
*
* @author jc
* @since 2019-06-21
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

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
            * 需中选(家)
            */
    private Integer biddingQuantity;

            /**
            * 发包价格
            */
    private BigDecimal outsourcingPrice;

            /**
            * 创建时间
            */
    private Date createTime;


}
