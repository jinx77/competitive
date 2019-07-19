package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.dao.*;
import com.xinzuo.competitive.excel.ExcelUtil;
import com.xinzuo.competitive.excel.pojo.DepositDB;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.pojo.Deposit;
import com.xinzuo.competitive.pojo.Information;
import com.xinzuo.competitive.pojo.Projects;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.DepositService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.util.CodeUtil;
import com.xinzuo.competitive.util.KeyUtil;
import com.xinzuo.competitive.util.RandomDigit;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.*;

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
    @Autowired
    CodeUtil codeUtil;

    //导入Excel保证金表
    @Transactional
    public int readExcel(MultipartFile excel, String projectsId){
        int i=0;
        log.info(excel.getName());

        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new DepositDB(),1,0);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }
        Deposit jc=new Deposit();
        BeanUtils.copyProperties(list.get(0),jc);


        if (jc.getDepositName()==null||jc.getDepositAccount()==null){
            throw new CompetitiveException("导入失败。。。请导入合法的保证金表");

        }
        if (!(jc.getDepositName().equals("来款户名")&&jc.getDepositAccount().equals("来款账号"))){

            throw new CompetitiveException("导入失败。。。请导入合法的保证金表");

        }

        //查出该项目所有的资格表
        Map<String,Qualification> map=new HashMap<>();
        QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
        qualificationQueryWrapper.eq("projects_id",projectsId);
        List<Qualification> qualificationList= qualificationDao.selectList(qualificationQueryWrapper);

        for (Qualification qualification:qualificationList){

            map.put(qualification.getQualificationName(),qualification);
        }
        for (Object o:list){
            Deposit deposit =new Deposit();
            BeanUtils.copyProperties(o,deposit);
            deposit.setProjectsId(projectsId);
            if (deposit.getDepositName()!=null&&deposit.getDepositName()!=""){
                deposit.setDepositName(codeUtil.stringFilter(deposit.getDepositName()));
            }

            if (deposit.getDepositMoney()==null&&StringUtils.isEmpty(deposit.getDepositName())){
                //throw new CompetitiveException("导入错误,请导入有数据的保证金表");
                continue;
            }
            if (deposit.getDepositName().equals("来款户名")){
                //throw new CompetitiveException("导入错误,请导入有数据的保证金表");
                continue;
            }

            //资格信息
            Qualification q=new Qualification();
            q.setQualificationId(KeyUtil.genUniqueKey());
            q.setProjectsId(projectsId);
            q.setQualificationName(deposit.getDepositName());
            Qualification qualification= map.get(deposit.getDepositName());
            //资格记录不存在时插入保证金表和资格表
            if (qualification==null){
                q.setDepositStatus(1);
                q.setQualificationStatus(0);
                q.setInformationStatus(0);
                q.setWinStatus(0);
                if (deposit.getDepositName()!=null){
                    i=  qualificationDao.insert(q);
                    map.put(q.getQualificationName(),q);
                }
            }
            //资格记录存在时更新记录
            if (qualification!=null){
                //判断资格表
                if (qualification.getInformationStatus()==1){
                    q.setQualificationStatus(1);
                    //插入抽选编号
                    if (qualification.getQualificationNumber()==null) {
                        q.setQualificationNumber(codeUtil.getCode(projectsId));
                    }else {
                        continue;
                    }
                    q.setDepositStatus(1);
                    q.setQualificationId(qualification.getQualificationId());
                    i= qualificationDao.updateById(q);
                }

            }
        }
        map.clear();
        System.out.println("readExcel读取后:   " + list);
        return i;
    }

   /* //导入Excel表
    @Transactional
    public int readExcel(MultipartFile excel, String projectsId){
        int i=0;
        log.info(excel.getName());

        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new DepositDB(),1,1);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }
        Deposit jc=new Deposit();
        BeanUtils.copyProperties(list.get(0),jc);


      *//*  if (!(jc.getDepositName().equals("来款户名")&&jc.getDepositAccount().equals("来款账号"))){

            throw new CompetitiveException("导入失败。。。请导入合法的保证金表");

        }*//*
        for (Object o:list){
            Deposit deposit =new Deposit();
            BeanUtils.copyProperties(o,deposit);
            deposit.setProjectsId(projectsId);
            if (deposit.getDepositName()!=null&&deposit.getDepositName()!=""){
                deposit.setDepositName(codeUtil.stringFilter(deposit.getDepositName()));
            }

            if (deposit.getDepositMoney()==null&& StringUtils.isEmpty(deposit.getDepositName())){
                //throw new CompetitiveException("导入错误,请导入有数据的保证金表");
                break;
            }
            String depositId=KeyUtil.genUniqueKey();
            deposit.setDepositId(depositId);
            log.info(depositId+"=depositId");

            QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
            qualificationQueryWrapper.eq("qualification_name",deposit.getDepositName())
                    .eq("projects_id",projectsId);
            Qualification qualification= qualificationDao.selectOne(qualificationQueryWrapper);
            //资格记录不存在时插入保证金表和资格表
            if (qualification==null){
                if (deposit.getDepositName()!=null){
                    depositDao.insert(deposit);
                }
                Qualification q=new Qualification();
                q.setQualificationId(KeyUtil.genUniqueKey());
                q.setProjectsId(projectsId);
                q.setDepositId(depositId);
                q.setQualificationName(deposit.getDepositName());
                q.setDepositStatus(1);
                q.setQualificationStatus(0);
                q.setInformationStatus(0);
                q.setWinStatus(0);
                if (deposit.getDepositName()!=null){
                  i=  qualificationDao.insert(q);
                }
            }

                //资格记录存在时更新记录
                if (qualification!=null){

                    Deposit deposit1= depositDao.selectById(qualification.getDepositId());
                    if (deposit1!=null){
                        deposit.setDepositId(deposit1.getDepositId());
                        depositDao.updateById(deposit);
                    }else {
                        //如果为空则插入公司信息记录
                        if (deposit.getDepositName()!=null){
                            depositDao.insert(deposit);
                            qualification.setDepositId(depositId);
                            qualification.setDepositStatus(1);
                        }
                    }
                    //判断资格表
                   Information information= informationDao.selectById(qualification.getInformationId());
                    if (information!=null){
                        qualification.setQualificationStatus(1);
                        //插入抽选编号
                        if (qualification.getQualificationNumber()==null) {
                            qualification.setQualificationNumber(codeUtil.getCode(projectsId));
                        }

                    }else {
                        qualification.setQualificationStatus(0);
                        qualification.setInformationStatus(0);
                    }

                    qualification.setDepositStatus(1);
                    qualification.setQualificationName(deposit.getDepositName());
                    i= qualificationDao.updateById(qualification);
                }
            }
        System.out.println("readExcel读取后:   " + list);
        return i;
    }*/

}
