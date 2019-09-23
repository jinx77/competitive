package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinzuo.competitive.dao.DepositDao;
import com.xinzuo.competitive.dao.InformationDao;
import com.xinzuo.competitive.dao.ProjectsDao;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.form.PullForm;
import com.xinzuo.competitive.pojo.*;
import com.xinzuo.competitive.dao.QualificationDao;
import com.xinzuo.competitive.service.CompanyClassifyService;
import com.xinzuo.competitive.service.CompanyService;
import com.xinzuo.competitive.service.QualificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.util.CodeUtil;
import com.xinzuo.competitive.util.KeyUtil;
import com.xinzuo.competitive.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.Kernel;
import java.lang.reflect.Array;
import java.util.*;

/**
 * <p>
 * 参选资格表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
@Slf4j
@Service
public class QualificationServiceImpl extends ServiceImpl<QualificationDao, Qualification> implements QualificationService {
    @Autowired
    ResultDataVO resultDataVO;
    @Autowired
    QualificationDao qualificationDao;
    @Autowired
    ProjectsDao projectsDao;
    @Autowired
    DepositDao depositDao;
    @Autowired
    InformationDao informationDao;
    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyClassifyService companyClassifyService;
    @Autowired
    CodeUtil codeUtil;

    @Override
    public ResultDataVO selectQualificationList(PageForm pageForm) {
        PageHelper.startPage(pageForm.getCurrent(), pageForm.getSize());
        List<Qualification> qualificationList= qualificationDao.selectQualificationList(pageForm);
        PageInfo<Qualification> pageInfo = new PageInfo<Qualification>(qualificationList);
        return resultDataVO.getResultDataVO(pageInfo);
    }

    @Override
    public int selectQualificationa(String projectsId) {
        return qualificationDao.selectQualificationa(projectsId);
    }

    @Override
    public int selectQualificationb(String projectsId) {
        return qualificationDao.selectQualificationb(projectsId);
    }
    //摇号
    @Override
    @Transactional
    public Qualification win(String projectsId) {
        Qualification qualification= qualificationDao.win(projectsId);
        if (qualification==null){
            throw  new CompetitiveException("有资格竞标的公司已抽完");
        }
        qualification.setWinStatus(1);
        Qualification q=new Qualification();
        q.setQualificationId(qualification.getQualificationId());
        q.setWinStatus(1);
        q.setWinTime(new Date());
        qualificationDao.updateById(q);
       Projects projects= projectsDao.selectById(projectsId);
       if (projects==null){
            throw  new ClassCastException("系统错误,该项目不存在");
       }
       if (projects.getWinQuantity()>=projects.getBiddingQuantity()){
           throw  new CompetitiveException("已抽完,不可以在抽");
       }
       projects.setWinQuantity(projects.getWinQuantity()+1);
        projects.setWinTime(new Date());
        projectsDao.updateById(projects);
        return qualification;
    }
    //删除
    @Override
    public int deleteQualification(String qualificationId) {
       Qualification qualification= qualificationDao.selectById(qualificationId);

       if (qualification==null){
           throw new CompetitiveException("删除企业发生错误,找不到该企业");
       }
        // informationDao.deleteById(qualification.getInformationId());
        // depositDao.deleteById(qualification.getDepositId());
       int i=  qualificationDao.deleteById(qualificationId);
        return i;
    }

    //拉入企业
    @Override
    public int pullQualification(PullForm pullForm) {
        if (pullForm.getProjectsId().equals("")||pullForm.getProjectsId()==null){
            throw new CompetitiveException("拉入错误,缺少加入项目ID");
        }
       Projects projects= projectsDao.selectById(pullForm.getProjectsId());
        if (!projects.getCompanyClassifyList().equals("")) {
            String[] strings = projects.getCompanyClassifyList().split(",");
            List<Integer> integerList0 = new ArrayList<>();
            for (String string : strings) {
                integerList0.add(Integer.valueOf(string));
            }
            List<Integer> integerList1 = pullForm.getList();
            List<Integer> list = new ArrayList<>();

            if (integerList1.size()==0){

                list=integerList0;

            }else {
                for (int i : integerList0) {
                    Boolean b = false;
                    for (int j : integerList1) {
                        if (i == j) {
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        list.add(i);
                    }
                }
            }
            list.forEach(integer -> {
                QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("company_classify_id", integer);
                List<Company> companyList = companyService.list(queryWrapper);
                companyList.forEach(company -> {
                    QueryWrapper<Qualification> qualificationQueryWrapper = new QueryWrapper<>();
                    qualificationQueryWrapper.eq("qualification_name", company.getProposerName());
                    qualificationQueryWrapper.eq("projects_id", pullForm.getProjectsId());
/*                    List<Qualification> qualifications= qualificationDao.selectList(qualificationQueryWrapper);
                    if (qualifications.size()>1){
                        qualifications.forEach(qualification -> {

                            if (qualification.getDepositStatus()==1){
                                Qualification q=new Qualification();
                                q.setQualificationId(qualification.getQualificationId());
                                q.setInformationStatus(0);
                                q.setQualificationNumber("");
                                q.setQualificationStatus(0);
                                qualificationDao.updateById(q);
                            }else {
                               qualificationDao.deleteById(qualification.getQualificationId());
                            }
                        });
                    }*/
                    qualificationDao.delete(qualificationQueryWrapper);
                });
            });
        }
          if (pullForm.getList().size()>0){
              Projects p=new  Projects();
              p.setProjectsId(pullForm.getProjectsId());
              StringBuffer s=new StringBuffer();
              for (Integer integer:pullForm.getList()){
                  s.append(integer.toString()+",");
              }
              p.setCompanyClassifyList(s.toString());
              projectsDao.updateById(p);
          }else {
              Projects p=new  Projects();
              p.setProjectsId(pullForm.getProjectsId());
              p.setCompanyClassifyList("");
              projectsDao.updateById(p);
              return 1;
          }


        //查出该项目所有的资格表
        QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
        qualificationQueryWrapper.eq("projects_id",pullForm.getProjectsId());
        List<Qualification> qualificationList= qualificationDao.selectList(qualificationQueryWrapper);
        Map<String,Qualification> map=new HashMap<>();
        qualificationList.forEach(qualification ->
            map.put(qualification.getQualificationName(),qualification)
        );
        pullForm.getList().forEach(integer -> {
            QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("company_classify_id",integer);
            List<Company> companyList= companyService.list(queryWrapper);
            companyList.forEach(company -> {
                if (company.getProposerName() != null && company.getProposerName() != "") {
                    company.setProposerName(codeUtil.stringFilter(company.getProposerName()));
                }
                //资格表
                Qualification q=new Qualification();
                q.setQualificationName(company.getProposerName());
                q.setPhone(company.getPhone());
                q.setInformationStatus(1);
                q.setLegalRepresentative(company.getLegalRepresentative());
                Qualification qualification=  map.get(company.getProposerName());
                if (qualification==null){
                    log.info("-----------不存在");
                    //如果资格表记录不存在
                    q.setQualificationId(KeyUtil.genUniqueKey());
                    q.setProjectsId(pullForm.getProjectsId());
                    q.setDepositStatus(0);
                    q.setQualificationStatus(0);
                    q.setWinStatus(0);
                    q.setCreateTime(new Date());
                    qualificationDao.insert(q);
                    map.put(q.getQualificationName(),q);
                }else {
                    //如果资格表记录存在
                    //判断保证金表表
                   if( qualification.getDepositStatus()==1){
                       //插入抽选编号
                       if (qualification.getQualificationNumber()==null) {
                           q.setQualificationStatus(1);
                           q.setQualificationNumber(codeUtil.getCode(pullForm.getProjectsId()));
                       }else {
                           //判断有没有变化
                           if (qualification.getPhone()!=null&&qualification.getLegalRepresentative()!=null) {
                               if (qualification.getQualificationName().equals(company.getProposerName())
                                       && qualification.getPhone().equals(company.getPhone())
                                       && qualification.getLegalRepresentative().equals(company.getLegalRepresentative())) {
                                   System.out.println("======资格信息表没有变化------------------");
                                   return;
                               }
                           }
                       }
                   }
                   else {
                       if (qualification.getPhone()!=null&&qualification.getLegalRepresentative()!=null) {
                           if (qualification.getQualificationName().equals(company.getProposerName())
                                   && qualification.getPhone().equals(company.getPhone())
                                   && qualification.getLegalRepresentative().equals(company.getLegalRepresentative())) {
                               System.out.println("=======资格信息表没有变化-----------------");
                               return;
                           }
                       }
                   }
                    q.setQualificationId(qualification.getQualificationId());
                    qualificationDao.updateById(q);
                }
            });
        });
        map.clear();
        return 1;
    }
   /* //拉入企业
    @Override
    public int pullQualification(PullForm pullForm) {
        log.info(pullForm.getProjectsId()+"-----------------");

        //查出该项目所有的公司信息表
        QueryWrapper<Information> informationQueryWrapper=new QueryWrapper<>();
        informationQueryWrapper.eq("projects_id",pullForm.getProjectsId());
        List<Information> informationList= informationDao.selectList(informationQueryWrapper);
        log.info("---");
        Map<String,Information> imap=new HashMap<>();
        informationList.forEach(information -> {
            imap.put(information.getProposerName(),information);
        });

        //查出该项目所有的资格表
        QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
        qualificationQueryWrapper.eq("projects_id",pullForm.getProjectsId());
        List<Qualification> qualificationList= qualificationDao.selectList(qualificationQueryWrapper);
        Map<String,Qualification> map=new HashMap<>();
        qualificationList.forEach(qualification -> {
            map.put(qualification.getQualificationName(),qualification);
        });



       pullForm.getList().forEach(integer -> {
            QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
          queryWrapper.eq("company_classify_id",integer);
            List<Company> companyList= companyService.list(queryWrapper);
            companyList.forEach(company -> {
                if (company.getProposerName()!=null&&company.getProposerName()!=""){
                    company.setProposerName(codeUtil.stringFilter(company.getProposerName()));
                }
                String informationId= KeyUtil.genUniqueKey();
                //资格表
                Qualification q=new Qualification();
                q.setQualificationName(company.getProposerName());
                q.setPhone(company.getPhone());
                q.setInformationId(informationId);
                q.setLegalRepresentative(company.getLegalRepresentative());
                //入库表
                Information information=new Information();
                information.setProposerName(company.getProposerName());
                information.setLegalRepresentative(company.getLegalRepresentative());
                information.setPhone(company.getPhone());
                information.setProjectsId(pullForm.getProjectsId());

              Qualification qualification=  map.get(company.getProposerName());
              if (qualification==null){
                  //如果资格表记录不存在
                  q.setQualificationId(KeyUtil.genUniqueKey());
                  q.setProjectsId(pullForm.getProjectsId());
                  q.setInformationStatus(1);
                  q.setDepositStatus(0);
                  q.setQualificationStatus(0);
                  q.setWinStatus(0);
                  q.setCreateTime(new Date());
                  qualificationDao.insert(q);
                  information.setInformationId(informationId);
                  int i= informationDao.insert(information);
              }else {
                  //如果资格表记录存在
                  //如果入库资料没有
                      Information information1=  imap.get(company.getProposerName());
                      if (information1==null){
                          information.setInformationId(informationId);
                          informationDao.insert(information);
                          imap.put(information.getProposerName(),information);
                          q.setInformationId(informationId);
                      }else {
                          //如果入库资料有
                          information.setInformationId(information1.getInformationId());

                          if (!(information1.getProposerName().equals(information.getProposerName())
                                  &&information1.getPhone().equals(information.getPhone())
                                  &&information1.getLegalRepresentative().equals(information.getLegalRepresentative()))){
                              information.setInformationId(information1.getInformationId());
                              informationDao.updateById(information);
                              System.out.println("======+++++222222222222+++++----------------");
                          }

                      }

                  //判断保证金表表
                 // Deposit deposit= depositDao.selectById(qualification.getDepositId());
                 // if (deposit!=null){
                  if (qualification.getDepositStatus()==1){
                      q.setQualificationStatus(1);
                      //插入抽选编号
                      if (qualification.getQualificationNumber()==null) {
                          q.setQualificationNumber(codeUtil.getCode(pullForm.getProjectsId()));
                      }
                  }
                  q.setQualificationId(qualification.getQualificationId());
                  qualificationDao.updateById(q);
              }
            });
        });
        return 1;
    }*/
}
