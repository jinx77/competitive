package com.xinzuo.competitive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.CompanyClassify;
import com.xinzuo.competitive.pojo.Projects;
import com.xinzuo.competitive.service.CompanyClassifyService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.PageVO;
import com.xinzuo.competitive.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 公司分类表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
@RestController
@RequestMapping("/company-classify")
public class CompanyClassifyController {
    @Autowired
    CompanyClassifyService companyClassifyService;
    @Autowired
    PageVO pageVO;
    //添加类型
    @PostMapping("/addClassify")
    public ResultVO addClassify(@RequestBody CompanyClassify companyClassify) {
       int code= companyClassifyService.list().size()+1;
       companyClassify.setClassifySort(code);
           Boolean b= companyClassifyService.save(companyClassify);
           if (b){
            return  ResultUtil.ok("添加成功");
           }else {
            return  ResultUtil.ok("系统出错,添加失败");
           }
    }
    //修改
    @PostMapping("/updateClassify")
    public ResultVO updateClassify(@RequestBody CompanyClassify companyClassify) {

        Boolean b= companyClassifyService.updateById(companyClassify);
        if (b){
            return  ResultUtil.ok("修改成功");
        }else {
            return  ResultUtil.ok("系统出错,操作失败");
        }
    }
    //删除
    @PostMapping("/deleteClassify")
    public ResultVO deleteClassify(@RequestBody CompanyClassify companyClassify) {
        int  i= companyClassifyService.deleteClassify(companyClassify.getClassifyId());
        if (i>0){
            return  ResultUtil.ok("删除成功");
        }else {
            return  ResultUtil.ok("系统出错,操作失败");
        }
    }

    //查询
    @PostMapping("/selectClassifyList")
    public ResultVO selectClassifyList(@RequestBody PageForm pageForm) {
        QueryWrapper<CompanyClassify> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("classify_sort");
        PageHelper.startPage(pageForm.getCurrent(), pageForm.getSize());
        List<CompanyClassify> companyClassifyList= companyClassifyService.list(queryWrapper);
        PageInfo<CompanyClassify> pageInfo = new PageInfo<>(companyClassifyList);
        PageVO p= pageVO.getPageVO0(pageInfo);
       return ResultUtil.ok("查询成功",p);

    }
}
