package com.xinzuo.competitive.serviceimpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.dao.*;
import com.xinzuo.competitive.excel.ExcelUtil;
import com.xinzuo.competitive.excel.pojo.InformationDB;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.pojo.Deposit;
import com.xinzuo.competitive.pojo.Information;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.InformationService;
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

    //导入Excel表
    @Transactional
    public int readExcel(MultipartFile excel, String projectsId){


        log.info(excel.getName());

        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new InformationDB(), 4,3);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }
        list.forEach(o->{
            Information information =new Information();
            BeanUtils.copyProperties(o,information);
            String informationId=KeyUtil.genUniqueKey();
            information.setInformationId(informationId);
            log.info(information.getInformationId()+"ididididi");
            QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
            qualificationQueryWrapper.eq("qualification_name",information.getProposerName())
                    .eq("projects_id",projectsId);
            Qualification qualification= qualificationDao.selectOne(qualificationQueryWrapper);

            //资格记录不存在时插入公司信息表和资格表
            if (qualification==null){
                if (information.getProposerName()!=null){
                    informationDao.insert(information);
                }
                Qualification q=new Qualification();
                q.setQualificationId(KeyUtil.genUniqueKey());
                q.setQualificationNumber("001");
                q.setProjectsId(projectsId);
                q.setQualificationName(information.getProposerName());
                q.setLegalRepresentative(information.getLegalRepresentative());
                q.setPhone(information.getPhone());
                q.setInformationStatus(1);
                q.setDepositStatus(0);
                q.setQualificationStatus(0);
                q.setInformationId(informationId);
                if (information.getProposerName()!=null){
                    qualificationDao.insert(q);
                }
            }
            else {
                //资格记录存在时更新记录
                if(qualification.getInformationId()!=null){
                    //查询公司信息记录
                   Information information1= informationDao.selectById(qualification.getProjectsId());
                   String infoId=information1.getInformationId();
                   //如果存在则更新公司信息记录
                   if (information1!=null){
                       BeanUtils.copyProperties(o,information1);
                       information1.setInformationId(infoId);
                       informationDao.updateById(information1);
                   }else {
                       //如果为空则插入公司信息记录
                       if (information.getProposerName()!=null){
                           informationDao.insert(information);
                           qualification.setInformationId(informationId);
                       }
                   }
                    //判断保证金表表
                    Deposit deposit= depositDao.selectById(qualification.getDepositId());
                    if (deposit!=null){
                        qualification.setQualificationStatus(1);
                    }
                }
                qualification.setQualificationName(information.getProposerName());
                qualification.setLegalRepresentative(information.getLegalRepresentative());
                qualification.setPhone(information.getPhone());
                qualification.setInformationStatus(1);
                qualificationDao.updateById(qualification);
            }
            log.info(information.toString()+"------");
        });

        System.out.println("readExcel读取后:   " + list);
        return 1;
   }
}
