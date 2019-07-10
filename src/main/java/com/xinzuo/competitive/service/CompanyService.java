package com.xinzuo.competitive.service;

import com.xinzuo.competitive.pojo.Company;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 企业信息表 服务类
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
public interface CompanyService extends IService<Company> {

    int readExcel(MultipartFile excel, int companyClassifyId);

}
