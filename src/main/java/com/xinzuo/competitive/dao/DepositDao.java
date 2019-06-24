package com.xinzuo.competitive.dao;

import com.xinzuo.competitive.pojo.Deposit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 保证金表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
public interface DepositDao extends BaseMapper<Deposit> {

    //统计数量
    @Select("select count(*) from deposit where projects_id=#{projectsId}")
    int selectDepositAmount(String projectsId);

}
