package com.xinzuo.competitive.service;

import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.CompanyClassify;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 公司分类表 服务类
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
public interface CompanyClassifyService extends IService<CompanyClassify> {
        int deleteClassify(int CompanyClassifyId);

}
