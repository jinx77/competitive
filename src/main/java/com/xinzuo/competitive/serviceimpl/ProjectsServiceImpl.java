package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.dao.DepositDao;
import com.xinzuo.competitive.dao.InformationDao;
import com.xinzuo.competitive.dao.QualificationDao;
import com.xinzuo.competitive.pojo.Deposit;
import com.xinzuo.competitive.pojo.Information;
import com.xinzuo.competitive.pojo.Projects;
import com.xinzuo.competitive.dao.ProjectsDao;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.ProjectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
@Service
public class ProjectsServiceImpl extends ServiceImpl<ProjectsDao, Projects> implements ProjectsService {
    @Autowired
    DepositDao depositDao;
    @Autowired
    InformationDao informationDao;
    @Autowired
    ProjectsDao projectsDao;
    @Autowired
    QualificationDao qualificationDao;
    @Override
    public int deleteProjects(String projectsId) {
        int i=  projectsDao.deleteById(projectsId);
        QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
        qualificationQueryWrapper.eq("projects_id",projectsId);
        qualificationDao.delete(qualificationQueryWrapper);

       /* QueryWrapper<Information> informationQueryWrapper=new QueryWrapper<>();
        informationQueryWrapper.eq("projects_id",projectsId);
        informationDao.delete(informationQueryWrapper);

        QueryWrapper<Deposit> depositQueryWrapper=new QueryWrapper<>();
        depositQueryWrapper.eq("projects_id",projectsId);
        depositDao.delete(depositQueryWrapper);*/

       return i;

    }
}
