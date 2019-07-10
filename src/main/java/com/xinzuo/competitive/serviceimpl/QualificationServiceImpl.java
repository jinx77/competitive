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
import java.util.Date;
import java.util.List;

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

    @Override
    public int deleteQualification(String qualificationId) {
       Qualification qualification= qualificationDao.selectById(qualificationId);

       if (qualification==null){
           throw new CompetitiveException("删除企业发生错误,找不到该企业");
       }
       informationDao.deleteById(qualification.getInformationId());
       depositDao.deleteById(qualification.getDepositId());
       int i=  qualificationDao.deleteById(qualificationId);
        return i;
    }
    //拉入企业
    @Override
    public int pullQualification(PullForm pullForm) {
       pullForm.getList().forEach(integer -> {
            QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
          queryWrapper.eq("company_classify_id",integer);
            List<Company> companyList= companyService.list(queryWrapper);
            companyList.forEach(company -> {
                String informationId= KeyUtil.genUniqueKey();
                //资格表
                Qualification q=new Qualification();
                q.setQualificationName(company.getProposerName());
                q.setPhone(company.getPhone());
                q.setInformationId(informationId);
                q.setLegalRepresentative(company.getLegalRepresentative());

               // q.setCreateTime(new Date());
                //入库表
                Information information=new Information();

                information.setProposerName(company.getProposerName());
                information.setLegalRepresentative(company.getLegalRepresentative());
                information.setPhone(company.getPhone());


                QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
                qualificationQueryWrapper.eq("qualification_name",company.getProposerName()).eq("projects_id",pullForm.getProjectsId());
              Qualification qualification=  qualificationDao.selectOne(qualificationQueryWrapper);

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
                  //如果入库资料有
                      QueryWrapper<Information> informationQueryWrapper=new QueryWrapper<>();
                      informationQueryWrapper.eq("information_id",qualification.getInformationId());
                      Information information1=  informationDao.selectOne(informationQueryWrapper);
                      if (information1==null){
                          information.setInformationId(informationId);
                          informationDao.insert(information);
                          q.setInformationId(informationId);
                      }else {
                          //如果入库资料没有
                          information.setInformationId(information1.getInformationId());
                          informationDao.updateById(information);
                      }

                  //判断保证金表表
                  Deposit deposit= depositDao.selectById(qualification.getDepositId());
                  if (deposit!=null){
                      log.info(deposit.getDepositName()+"777777777777777777777777");
                      q.setQualificationStatus(1);
                      //插入抽选编号
                      q.setQualificationNumber(codeUtil.getCode(pullForm.getProjectsId()));
                  }
                  q.setQualificationId(qualification.getQualificationId());
                  qualificationDao.updateById(q);
              }
            });
        });
        return 1;
    }
}
