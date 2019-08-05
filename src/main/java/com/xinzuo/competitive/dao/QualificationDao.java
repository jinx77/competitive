package com.xinzuo.competitive.dao;

import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.Qualification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 参选资格表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
public interface QualificationDao extends BaseMapper<Qualification> {


    List<Qualification> selectQualificationList(PageForm pageForm);

    //统计总参与公司数量
    @Select("select count(*) from qualification where projects_id=#{projectsId} and deposit_status=1")
    int selectQualificationa(String projectsId);

    //统计实际参与公司数量
    @Select("select count(*) from qualification where projects_id=#{projectsId} and qualification_status=1")
    int selectQualificationb(String projectsId);
    //统计已抽中公司数量
    @Select("select count(*) from qualification where projects_id=#{projectsId} and qualification_status=1")
    int selectQualificationWin(String projectsId);
    //抽奖
    @Select(" SELECT * FROM qualification WHERE projects_id=#{projectsId} and qualification_status=1 AND win_status=0 ORDER BY RAND() LIMIT 1")
    Qualification win(String projectsId);

}
