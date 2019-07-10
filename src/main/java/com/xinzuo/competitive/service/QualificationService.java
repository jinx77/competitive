package com.xinzuo.competitive.service;

import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.form.PullForm;
import com.xinzuo.competitive.pojo.Qualification;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinzuo.competitive.vo.ResultDataVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 参选资格表 服务类
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
public interface QualificationService extends IService<Qualification> {
    ResultDataVO selectQualificationList(PageForm pageForm);

    //统计总参与公司数量
    int selectQualificationa(String projectsId);

    //统计实际参与公司数量
    int selectQualificationb(String projectsId);
    //抽奖
    Qualification win(String projectsId);
    //删除
    int deleteQualification(String qualificationId);
    //拉入公司
    int pullQualification(PullForm pullForm);
}
