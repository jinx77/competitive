package com.xinzuo.competitive.dao;

import com.xinzuo.competitive.pojo.Information;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 承包商信息库表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
public interface InformationDao extends BaseMapper<Information> {
    //统计数量
    @Select("select count(*) from information where projects_id=#{projectsId}")
    int selectInformationAmount(String projectsId);
}
