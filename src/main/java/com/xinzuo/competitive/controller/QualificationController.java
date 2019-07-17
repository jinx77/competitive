package com.xinzuo.competitive.controller;


import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.form.PullForm;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.QualificationService;
import com.xinzuo.competitive.util.CodeUtil;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.PageVO;
import com.xinzuo.competitive.vo.ResultVO;
import com.xinzuo.competitive.vo.WinVO;
import com.xinzuo.competitive.vo.WinVO0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 参选资格表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
@CrossOrigin
@RestController
@RequestMapping("/qualification")
public class QualificationController {
    @Autowired
    QualificationService qualificationService;
    @Autowired
    CodeUtil codeUtil;

    //查询参与公司
    @PostMapping("/selectQualificationList")
    public ResultVO selectQualificationList(@RequestBody PageForm pageForm){
        return ResultUtil.ok("显示列表成功",qualificationService.selectQualificationList(pageForm));
    }
    //删除参与公司
    @PostMapping("/deleteQualification")
    public ResultVO deleteQualification(@RequestBody Qualification qualification){
      int i= qualificationService.deleteQualification(qualification.getQualificationId());
      if (i>0){
          return ResultUtil.ok("删除成功");
      }
        return ResultUtil.no("系统繁忙,请稍后再试");
    }

    //批量删除参与公司
    @PostMapping("/deleteQualificationList")
    public ResultVO deleteQualificationList(@RequestBody List<String> list){

       Boolean b= qualificationService.removeByIds(list);

        if (b){
            return ResultUtil.ok("删除成功");
        }
        return ResultUtil.no("系统繁忙,请稍后再试");
    }

    //拉取公司类型企业
    @PostMapping("/pullQualification")
    public ResultVO pullQualification(@RequestBody PullForm pullForm){
       int i= qualificationService.pullQualification(pullForm);

       if (i>0){

          return ResultUtil.ok("拉取成功");
       }else {

           return ResultUtil.ok("系统错误");
       }
    }

    //添加抽选
    @PostMapping("/addQualification")
    public ResultVO addQualification(@RequestBody Qualification qualification){

        if (qualification.getQualificationId()==null){
            return ResultUtil.no("系统繁忙 请稍候再试");
        }
       Qualification q= qualificationService.getById(qualification.getQualificationId());
        if (q==null){
            return ResultUtil.no("系统繁忙 请稍候再试");

        }
        q.setDepositStatus(1);
        q.setInformationStatus(1);
        q.setQualificationStatus(1);
        q.setQualificationNumber(codeUtil.getCode(q.getProjectsId()));
       Boolean b= qualificationService.updateById(q);
       if (b){
           return ResultUtil.ok("操作成功");

       }
        return ResultUtil.no("系统繁忙 请稍候再试");
    }

    //抽奖
    @PostMapping("/win")
    public ResultVO win(@RequestBody PageForm pageForm){

        Qualification qualification=qualificationService.win(pageForm.getProjectsId());

        if (qualification==null){

            ResultUtil.no("系统错误,抽奖失败");
        }

        WinVO0 winVO=new WinVO0();
        winVO.setQualificationName(qualification.getQualificationName());
        winVO.setQualificationNumber(qualification.getQualificationNumber());
        return ResultUtil.ok("显示抽中信息",winVO);


    }





}
