package com.xinzuo.competitive.dao;

import com.xinzuo.competitive.pojo.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 企业信息表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
public interface CompanyDao extends BaseMapper<Company> {
    @Select("select count(*) from company where company_classify_id=#{companyClassifyId}")
    int companyQuantity(int companyClassifyId);

}
