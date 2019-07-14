package com.xinzuo.competitive.pojo;

    import java.io.Serializable;

    import com.alibaba.excel.metadata.BaseRowModel;
    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 承包商信息库表
    * </p>
*
* @author jc
* @since 2019-06-22
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Information  implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键ID
            */
            @TableId
    private String informationId;

            /**
            * 申请人名称
            */
    private String proposerName;

            /**
            * 法定代表人
            */
    private String legalRepresentative;

            /**
            * 联系电话
            */
    private String phone;
            /**
            * 竞选项目ID
            */
    private String projectsId;


}
