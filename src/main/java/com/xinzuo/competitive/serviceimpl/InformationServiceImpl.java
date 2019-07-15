package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.dao.*;
import com.xinzuo.competitive.excel.ExcelUtil;
import com.xinzuo.competitive.excel.pojo.InformationDB;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.pojo.Information;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.InformationService;
import com.xinzuo.competitive.util.CodeUtil;
import com.xinzuo.competitive.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 承包商信息库表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
@Slf4j
@Service
public class InformationServiceImpl extends ServiceImpl<InformationDao, Information> implements InformationService {
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

    //导入Excel表
    @Transactional
    public int readExcel(MultipartFile excel, String projectsId){

        log.info(excel.getName());
        Map<String,Qualification> map=new HashMap<>();
        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new InformationDB(), 1,2);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }

        Information jc =new Information();
        BeanUtils.copyProperties(list.get(0),jc);

       /* if (!(jc.getProposerName().equals("申请人名称")&&jc.getLegalRepresentative().equals("法定代表人")&&jc.getPhone().equals("联系电话"))){
            throw new CompetitiveException("导入失败。。。请导入合法的公司资料表");
        }*/
        //查出该项目所有的资格表
        QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
        qualificationQueryWrapper.eq("projects_id",projectsId);
        List<Qualification> qualificationList= qualificationDao.selectList(qualificationQueryWrapper);

        qualificationList.forEach(qualification ->
            map.put(qualification.getQualificationName(),qualification)
        );
        list.forEach(o->{
            Information information =new Information();
            BeanUtils.copyProperties(o,information);
            if (information.getPhone()==null){
                // throw new CompetitiveException("导入错误,请导入有数据正确格式的保证金表");
                return;
            }
            if (!StringUtils.isEmpty(information.getProposerName())&&!KeyUtil.isNumeric(information.getPhone())){
                // throw new CompetitiveException("导入错误,请导入有数据正确格式的保证金表");
                return;
            }
            if (information.getProposerName()!=null&&information.getProposerName()!=""){
                information.setProposerName(codeUtil.stringFilter(information.getProposerName()));
            }
            //资格表
            Qualification q=new Qualification();
            q.setQualificationId(KeyUtil.genUniqueKey());
            q.setProjectsId(projectsId);
            q.setQualificationName(information.getProposerName());
            q.setLegalRepresentative(information.getLegalRepresentative());
            q.setPhone(information.getPhone());

            //资格记录不存在时插入公司信息表和资格表
            Qualification qualification=map.get(information.getProposerName());
            if (qualification==null){
                q.setInformationStatus(1);
                q.setDepositStatus(0);
                q.setQualificationStatus(0);
                q.setWinStatus(0);
                q.setCreateTime(new Date());
                if (information.getProposerName()!=null){
                    qualificationDao.insert(q);
                    map.put(q.getQualificationName(),q);
                }
            }
            //资格记录存在时更新记录
            if (qualification!=null){
                //判断保证金表表
                if (qualification.getDepositStatus()==1){
                    q.setQualificationStatus(1);
                    //插入抽选编号
                    if (qualification.getQualificationNumber()==null) {
                        q.setQualificationNumber(codeUtil.getCode(projectsId));
                    }else {
                        if (qualification.getPhone()!=null&&qualification.getLegalRepresentative()!=null) {
                            if (qualification.getQualificationName().equals(information.getProposerName())
                                    && qualification.getPhone().equals(information.getPhone())
                                    && qualification.getLegalRepresentative().equals(information.getLegalRepresentative())) {
                                System.out.println("======资格信息表没有变化-----------------");
                                return;
                            }
                        }
                    }
                }else {
                    if (qualification.getPhone()!=null&&qualification.getLegalRepresentative()!=null) {
                        if (qualification.getQualificationName().equals(information.getProposerName())
                                && qualification.getPhone().equals(information.getPhone())
                                && qualification.getLegalRepresentative().equals(information.getLegalRepresentative())) {
                            System.out.println("======资格信息表没有变化----------------");
                            return;
                        }
                    }
                }
                q.setInformationStatus(1);
                q.setQualificationId(qualification.getQualificationId());
                qualificationDao.updateById(q);
                }


        });
       map.clear();
        System.out.println("readExcel读取后:   " + list);
        return 1;
    }

    //导入Excel表
   /* @Transactional
    public int readExcel(MultipartFile excel, String projectsId){

        log.info(excel.getName());

        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new InformationDB(), 1,2);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }

        Information jc =new Information();
        BeanUtils.copyProperties(list.get(0),jc);

        if (!(jc.getProposerName().equals("公司名称")&&jc.getLegalRepresentative().equals("法定代表人")&&jc.getPhone().equals("联系电话"))){
            throw new CompetitiveException("导入失败。。。请导入合法的公司资料表");
        }
        //查出该项目所有的资格表
        QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
        qualificationQueryWrapper.eq("projects_id",projectsId);
       List<Qualification> qualificationList= qualificationDao.selectList(qualificationQueryWrapper);
        Map<String,Qualification> map=new HashMap<>();
        qualificationList.forEach(qualification -> {
            map.put(qualification.getQualificationName(),qualification);
        });
        //查出该项目所有的公司信息表
        QueryWrapper<Information> informationQueryWrapper=new QueryWrapper<>();
        informationQueryWrapper.eq("projects_id",projectsId);
        List<Information> informationList= informationDao.selectList(informationQueryWrapper);
        Map<String,Information> imap=new HashMap<>();
        informationList.forEach(information -> {
            imap.put(information.getProposerName(),information);
        });
        list.forEach(o->{
            Information information =new Information();
            BeanUtils.copyProperties(o,information);

            if (!StringUtils.isEmpty(information.getProposerName())&&!KeyUtil.isNumeric(information.getPhone())){
                // throw new CompetitiveException("导入错误,请导入有数据正确格式的保证金表");
                return;
            }
            if (information.getProposerName()!=null&&information.getProposerName()!=""){
                information.setProposerName(codeUtil.stringFilter(information.getProposerName()));
            }

            String informationId=KeyUtil.genUniqueKey();
            information.setProjectsId(projectsId);
            information.setInformationId(informationId);
            //资格记录不存在时插入公司信息表和资格表
            Qualification qualification=map.get(information.getProposerName());
            if (qualification==null){
                if (information.getProposerName()!=null){
                    informationDao.insert(information);
                    imap.put(information.getProposerName(),information);
                }
                Qualification q=new Qualification();
                q.setQualificationId(KeyUtil.genUniqueKey());
                q.setProjectsId(projectsId);
                q.setQualificationName(information.getProposerName());
                q.setLegalRepresentative(information.getLegalRepresentative());
                q.setPhone(information.getPhone());
                q.setInformationStatus(1);
                q.setDepositStatus(0);
                q.setQualificationStatus(0);
                q.setWinStatus(0);
                q.setInformationId(informationId);
                if (information.getProposerName()!=null){
                    qualificationDao.insert(q);
                    map.put(q.getQualificationName(),q);
                }

            }
                //资格记录存在时更新记录
            if (qualification!=null){
                    //查询公司信息记录
                   Information information1= imap.get(information.getProposerName());

                   String qInformationId=qualification.getInformationId();
                String qn=qualification.getQualificationNumber();
                   //如果存在则更新公司信息记录
                   if (information1!=null){
                       if (!(information1.getProposerName().equals(information.getProposerName())
                               &&information1.getPhone().equals(information.getPhone())
                               &&information1.getLegalRepresentative().equals(information.getLegalRepresentative()))){
                           information.setInformationId(information1.getInformationId());
                           informationDao.updateById(information);
                           System.out.println("======+++++222222222222+++++----------------");
                       }

                   }else {
                       //如果为空则插入公司信息记录
                       if (information.getProposerName()!=null){
                           System.out.println("======+++++33333333333333+++++----------------");
                           informationDao.insert(information);
                           qualification.setInformationId(informationId);
                       }
                   }
                    //判断保证金表表
                    Integer qs=qualification.getQualificationStatus();
                    if (qualification.getDepositStatus()==1){
                        qualification.setQualificationStatus(1);
                        //插入抽选编号
                        if (qualification.getQualificationNumber()==null) {
                            qualification.setQualificationNumber(codeUtil.getCode(projectsId));
                        }
                    }else {
                        qualification.setQualificationStatus(0);
                        //qualification.setDepositStatus(0);
                    }

                    //判断有没有变化
                  *//*  if (qualification.getQualificationName().equals(information.getProposerName())
                            &&qualification.getPhone().equals(information.getPhone())
                            &&qualification.getLegalRepresentative().equals(information.getLegalRepresentative())
                            &&qs==qualification.getQualificationStatus()
                            &&qualification.getInformationId().equals(qInformationId)
                            &&qualification.getInformationStatus()==1
                            &&qualification.getQualificationNumber().equals(qn)){
                        System.out.println("======资格信息表没有变化----------------");
                       return;
                    }*//*
                    qualification.setQualificationName(information.getProposerName());
                    qualification.setLegalRepresentative(information.getLegalRepresentative());
                    qualification.setPhone(information.getPhone());
                    qualification.setInformationStatus(1);
                    qualificationDao.updateById(qualification);
                }
        });

        System.out.println("readExcel读取后:   " + list);
        return 1;
   }*/


}
