package com.xinzuo.competitive.pojo;

    import java.io.Serializable;
    import java.util.Date;

    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 参选资格表
    * </p>
*
* @author jc
* @since 2019-06-22
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Qualification implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键ID
            */
            @TableId
    private String qualificationId;

            /**
            * 竞选编号
            */
    private String qualificationNumber;

            /**
            * 竞选项目ID
            */
    private String projectsId;
            /**
            * 信息库ID
            */
    private String informationId;
            /**
            * 保证金ID
            */
    private String depositId;

            /**
            * 参选公司名称
            */
    private String qualificationName;

            /**
            * 法定代表人
            */
    private String legalRepresentative;

            /**
            * 联系电话
            */
    private String phone;

            /**
            * 入库资料,0未传,1已传
            */
    private Integer informationStatus;

            /**
            * 保证金 0未缴纳,1已缴纳
            */
    private Integer depositStatus;

            /**
            * 竞选资格0没有资格参与 1有资格参与
            */
    private Integer qualificationStatus;

            /**
            * 是否中标  1中标
            */
    private Integer winStatus;

            /**
            * 中标时间
            */
    private Date winTime;

            /**
            * 创建时间
            */
    private Date createTime;
}
