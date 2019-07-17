package com.xinzuo.competitive.serviceimpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.dao.DepositDao;
import com.xinzuo.competitive.dao.InformationDao;
import com.xinzuo.competitive.dao.QualificationDao;
import com.xinzuo.competitive.excel.ExcelUtil;
import com.xinzuo.competitive.excel.pojo.ProjectsDB;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.pojo.Projects;
import com.xinzuo.competitive.dao.ProjectsDao;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.ProjectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * 项目表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
@Slf4j
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

    @Override
    public int readExcel(MultipartFile excel) {

        log.info(excel.getName());
        Map<String, Qualification> map = new HashMap<>();
        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new ProjectsDB(), 1, 1);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }

         /* if (!(jc.getProposerName().equals("申请人名称")&&jc.getLegalRepresentative().equals("法定代表人")&&jc.getPhone().equals("联系电话"))){
            throw new CompetitiveException("导入失败。。。请导入合法的公司资料表");
        }*/
        for (Object o : list) {
            Projects projects=new Projects();
            BeanUtils.copyProperties(o,projects);

        /*    if (projects.getBiddingQuantity()==null|| !KeyUtil.isNumeric(projects.getBiddingQuantity().toString())){


            }*/
            projects.setProjectsId(KeyUtil.genUniqueKey());
            projects.setWinQuantity(0);
            projects.setCreateTime(new Date());
          int i=  projectsDao.insert(projects);
        }

        return 1;
    }
}
