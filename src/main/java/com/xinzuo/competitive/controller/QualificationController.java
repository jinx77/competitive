package com.xinzuo.competitive.controller;


import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.QualificationService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import com.xinzuo.competitive.vo.WinVO;
import com.xinzuo.competitive.vo.WinVO0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //查询参与公司
    @PostMapping("/selectQualificationList")
    public ResultVO selectQualificationList(@RequestBody PageForm pageForm){
        return ResultUtil.ok("显示列表成功",qualificationService.selectQualificationList(pageForm));
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
