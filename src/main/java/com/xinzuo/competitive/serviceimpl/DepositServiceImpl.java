package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.dao.*;
import com.xinzuo.competitive.excel.ExcelUtil;
import com.xinzuo.competitive.excel.pojo.DepositDB;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.pojo.Deposit;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.DepositService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 保证金表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
@Slf4j
@Service
public class DepositServiceImpl extends ServiceImpl<DepositDao, Deposit> implements DepositService {
    @Autowired
    InformationDao informationDao;
    @Autowired
    ProjectsDao projectsDao;
    @Autowired
    QualificationDao qualificationDao;
    @Autowired
    ProjectsQualificationDao projectsQualificationDao;
    @Autowired
    DepositDao depositDao;

    //导入Excel表
    @Transactional
    public int readExcel(MultipartFile excel, String projectsId){


        log.info(excel.getName());

        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new DepositDB(),1,1);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }
        list.forEach(o->{
            Deposit deposit =new Deposit();
            BeanUtils.copyProperties(o,deposit);
            deposit.setDepositId(KeyUtil.genUniqueKey());

            QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
            qualificationQueryWrapper.eq("qualification_name",deposit.getDepositName())
                    .eq("projects_id",projectsId);
            Qualification qualification= qualificationDao.selectOne(qualificationQueryWrapper);
            if (qualification==null){
                depositDao.insert(deposit);
                Qualification q=new Qualification();
                q.setQualificationId(KeyUtil.genUniqueKey());
                log.info("-----");
                q.setQualificationNumber("001");
                q.setProjectsId(projectsId);
                q.setQualificationName(deposit.getDepositName());
                q.setDepositStatus(1);
                q.setDepositStatus(0);
                if (deposit.getDepositName()!=null){
                    qualificationDao.insert(q);
                }

            }else {
                qualification.setQualificationName(deposit.getDepositName());
                qualificationDao.updateById(qualification);
            }
        });

        System.out.println("readExcel读取后:   " + list);
        return 1;



    }
}
