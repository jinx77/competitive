package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.excel.ExcelUtil;
import com.xinzuo.competitive.excel.pojo.CompanyDB;
import com.xinzuo.competitive.excel.pojo.InformationDB;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.pojo.Company;
import com.xinzuo.competitive.dao.CompanyDao;
import com.xinzuo.competitive.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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
    @Override
    public int readExcel(MultipartFile excel, int companyClassifyId) {
        log.info(excel.getName()+"-------======================");

        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new CompanyDB(), 1,2);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }
        Company jc=new Company();
        BeanUtils.copyProperties(list.get(0),jc);

        if (!(jc.getProposerName().equals("公司名称")&&jc.getLegalRepresentative().equals("法定代表人")&&jc.getPhone().equals("联系电话"))){

            throw new CompetitiveException("导入失败。。。请导入合法的公司资料表");
        }

        list.forEach(o -> {
            Company company=new Company();
            BeanUtils.copyProperties(o,company);
            if (!StringUtils.isEmpty(company.getProposerName())&&!KeyUtil.isNumeric(company.getPhone())){
               // throw new CompetitiveException("导入错误,请导入有数据正确格式的企业信息表");
                return;
            }
            if (company.getProposerName()!=null){
                QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("proposer_name",company.getProposerName());
                Company c= companyDao.selectOne(queryWrapper);
                if (c==null){
                    company.setCompanyId(KeyUtil.genUniqueKey());
                    company.setCompanyClassifyId(companyClassifyId);
                    company.setCreateTime(new Date());
                    companyDao.insert(company);
                }
            }
        });
        return 1;
    }
}
