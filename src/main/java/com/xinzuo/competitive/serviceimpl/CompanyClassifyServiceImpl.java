package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.pojo.CompanyClassify;
import com.xinzuo.competitive.dao.CompanyClassifyDao;
import com.xinzuo.competitive.service.CompanyClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 公司分类表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
@Service
public class CompanyClassifyServiceImpl extends ServiceImpl<CompanyClassifyDao, CompanyClassify> implements CompanyClassifyService {
    @Autowired
    CompanyClassifyDao companyClassifyDao;
    @Override
    @Transactional
    //删除分类 并重新排序
    public int deleteClassify(int companyClassifyId) {
      int c=  companyClassifyDao.deleteById(companyClassifyId);
        List<CompanyClassify> companyClassifyList= list();
        List<Integer> codes=new ArrayList<>();
        companyClassifyList.forEach(companyClassify -> codes.add(companyClassify.getClassifySort()));
        Collections.sort(codes);
        for (int i=0;i<companyClassifyList.size();i++){
           CompanyClassify classify=new CompanyClassify();
           classify.setClassifyId(companyClassifyList.get(i).getClassifyId());
           classify.setClassifySort(i+1);
            companyClassifyDao.updateById(classify);
        }

        return c;
    }
}
