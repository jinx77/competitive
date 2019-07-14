package com.xinzuo.competitive.pojo;

    import java.math.BigDecimal;
    import java.io.Serializable;

    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 保证金表
    * </p>
*
* @author jc
* @since 2019-06-21
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Deposit implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键ID
            */
            @TableId
    private String depositId;

            /**
            * 来款户名
            */
    private String depositName;

            /**
            * 来款账号
            */
    private String depositAccount;

            /**
            * 来款银行
            */
    private String depositBank;

            /**
            * 到账时间
            */
    private String intoTime;

            /**
            * 汇款途径
            */
    private String remitPathway;

            /**
            * 保证金额度
            */
    private BigDecimal depositMoney;
            /**
            * 竞选项目ID
            */
    private String projectsId;


}
