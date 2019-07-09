package com.xinzuo.competitive.serviceimpl;

import com.xinzuo.competitive.pojo.Company;
import com.xinzuo.competitive.dao.CompanyDao;
import com.xinzuo.competitive.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业信息表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, Company> implements CompanyService {

}
