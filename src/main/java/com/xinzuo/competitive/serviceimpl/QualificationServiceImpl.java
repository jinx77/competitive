package com.xinzuo.competitive.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinzuo.competitive.dao.ProjectsDao;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.Projects;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.dao.QualificationDao;
import com.xinzuo.competitive.service.QualificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.vo.ResultDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class QualificationServiceImpl extends ServiceImpl<QualificationDao, Qualification> implements QualificationService {
    @Autowired
    ResultDataVO resultDataVO;
    @Autowired
    QualificationDao qualificationDao;
    @Autowired
    ProjectsDao projectsDao;
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
}
