package com.xinzuo.competitive.dao;

import com.xinzuo.competitive.pojo.Qualification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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


    List<Qualification> selectQualificationList();

}
