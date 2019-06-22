package com.xinzuo.competitive.pojo;

    import java.io.Serializable;

    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 公司资质项目关联表
    * </p>
*
* @author jc
* @since 2019-06-22
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class ProjectsQualification implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键ID
            */
            @TableId
    private String projectsQualificationId;

            /**
            * 项目主键ID
            */
    private String projectsId;

            /**
            * 竞选公司资格主键ID
            */
    private String qualificationId;


}
