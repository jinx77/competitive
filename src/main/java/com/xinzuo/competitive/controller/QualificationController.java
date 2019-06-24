package com.xinzuo.competitive.controller;


import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.service.QualificationService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 参选资格表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
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

        return null;



    }





}
