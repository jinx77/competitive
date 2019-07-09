package com.xinzuo.competitive.pojo;

    import java.io.Serializable;
    import java.util.Date;

    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 企业信息表
    * </p>
*
* @author jc
* @since 2019-07-09
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键ID
            */
            @TableId
    private String companyId;

            /**
            * 分类主键ID
            */
    private Integer companyClassifyId;

            /**
            * 公司名称
            */
    private String proposerName;

            /**
            * 法定代表人
            */
    private String legalRepresentative;

            /**
            * 电话
            */
    private String phone;
            /**
            * 创建时间
            */
    private Date createTime;


}
