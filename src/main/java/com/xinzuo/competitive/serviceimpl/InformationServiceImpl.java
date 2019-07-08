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
import com.xinzuo.competitive.util.CodeUtil;
import com.xinzuo.competitive.util.KeyUtil;
import com.xinzuo.competitive.util.RandomDigit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    @Autowired
    CodeUtil codeUtil;

    //导入Excel表
    @Transactional
    public int readExcel(MultipartFile excel, String projectsId){


        log.info(excel.getName());

        List<Object> list = null;

        try {
            list = ExcelUtil.readExcel(excel, new InformationDB(), 1,3);
        } catch (Exception e) {

            e.printStackTrace();
            throw new CompetitiveException("导入失败");
        }
        list.forEach(o->{
            Information information =new Information();
            BeanUtils.copyProperties(o,information);
            if (!StringUtils.isEmpty(information.getProposerName())&&!KeyUtil.isNumeric(information.getPhone())){
                throw new CompetitiveException("导入错误,请导入有数据正确格式的保证金表");
            }
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
               /* String code= RandomDigit.getfiveVerificationCode3();
                QueryWrapper<Qualification> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("qualification_number",code).eq("projects_id",projectsId);
                int c=1;
                while (c==0){
                    List<Qualification> qualificationList= qualificationDao.selectList(queryWrapper);
                    c=qualificationList.size();
                    if (c>0) {
                        code = RandomDigit.getfiveVerificationCode3();
                    }
                }*/
               // q.setQualificationNumber(code);
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
                }
            }

                //资格记录存在时更新记录
                if(qualification!=null){
                    //查询公司信息记录
                   Information information1= informationDao.selectById(qualification.getProjectsId());
                  // String infoId=information1.getInformationId();
                   //如果存在则更新公司信息记录
                   if (information1!=null){
                      // BeanUtils.copyProperties(o,information1);
                      // information1.setInformationId(infoId);
                       information.setInformationId(information1.getInformationId());
                       informationDao.updateById(information1);
                   }else {
                       //如果为空则插入公司信息记录
                       if (information.getProposerName()!=null){
                           informationDao.insert(information);
                           qualification.setInformationId(informationId);
                           qualification.setInformationStatus(1);
                       }
                   }
                    //判断保证金表表
                    Deposit deposit= depositDao.selectById(qualification.getDepositId());
                    if (deposit!=null){
                        qualification.setQualificationStatus(1);
                        //插入抽选编号
                        qualification.setQualificationNumber(codeUtil.getCode(projectsId));
                    }

                    if (qualification.getDepositStatus()==1){
                        qualification.setQualificationStatus(1);
                    }
                    qualification.setQualificationName(information.getProposerName());
                    qualification.setLegalRepresentative(information.getLegalRepresentative());
                    qualification.setPhone(information.getPhone());
                    qualification.setInformationStatus(1);
                    qualification.setInformationId(informationId);
                    qualificationDao.updateById(qualification);
                }
            log.info(information.toString()+"------");
        });

        System.out.println("readExcel读取后:   " + list);
        return 1;
   }


}
