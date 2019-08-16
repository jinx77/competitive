package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.excel.ExcelUtil;
import com.xinzuo.competitive.excel.pojo.CompanyDB;
import com.xinzuo.competitive.excel.pojo.CompanyDB1;
import com.xinzuo.competitive.excel.pojo.InformationDB;
import com.xinzuo.competitive.excel.pojo.InformationDB1;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.pojo.Company;
import com.xinzuo.competitive.dao.CompanyDao;
import com.xinzuo.competitive.pojo.Information;
import com.xinzuo.competitive.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.util.CodeUtil;
import com.xinzuo.competitive.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业信息表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, Company> implements CompanyService {
    @Autowired
    CompanyDao companyDao;
    @Autowired
    CodeUtil codeUtil;
    //导入公司企业
    @Override
    public int readExcel(MultipartFile excel, int companyClassifyId) {
        log.info(excel.getName()+"-------======================");

        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new CompanyDB(), 1,0);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }

        Company jc =new Company();
        BeanUtils.copyProperties(list.get(0),jc);
        if (jc.getProposerName().equals("序号")){
            try {
                list = ExcelUtil.readExcel(excel, new CompanyDB1(), 1,0);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CompetitiveException("导入失败");
            }
            BeanUtils.copyProperties(list.get(0),jc);
        }
        if (!jc.getProposerName().equals("申请人名称")){
            throw new CompetitiveException("请导入正确的信息表");
        }

        QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("company_classify_id",companyClassifyId);
        List<Company> companyList= companyDao.selectList(queryWrapper);
        Map<String,Company> map=new HashMap<>();
        companyList.forEach(company ->
            map.put(company.getProposerName(),company)
        );
        list.forEach(o -> {
            Company company=new Company();
            BeanUtils.copyProperties(o,company);
            if(company.getProposerName().equals("申请人名称")){
                // throw new CompetitiveException("导入错误,请导入有数据正确格式的保证金表");
                return;
            }
            if (company.getProposerName()!=null){

                Company c= map.get(company.getProposerName());
                if (c==null){
                    company.setCompanyId(KeyUtil.genUniqueKey());
                    company.setCompanyClassifyId(companyClassifyId);
                    company.setCreateTime(new Date());
                    company.setCompanyNumber(codeUtil.getCode0(companyClassifyId));
                    companyDao.insert(company);
                    map.put(company.getProposerName(),company);
                }
            }
        });
        map.clear();
        return 1;
    }

    @Override
    public int companyQuantity(int companyClassifyId) {
        return companyDao.companyQuantity(companyClassifyId);
    }
}
