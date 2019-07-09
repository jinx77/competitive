package com.xinzuo.competitive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.Company;
import com.xinzuo.competitive.pojo.CompanyClassify;
import com.xinzuo.competitive.service.CompanyService;
import com.xinzuo.competitive.util.KeyUtil;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.PageVO;
import com.xinzuo.competitive.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.awt.image.Kernel;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 企业信息表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;
    @Autowired
    PageVO pageVO;
    //添加类型
    @PostMapping("/addCompany")
    public ResultVO addCompany(@RequestBody Company company) {
       company.setCompanyId(KeyUtil.genUniqueKey());
       company.setCreateTime(new Date());
        Boolean b= companyService.save(company);
        if (b){
            return  ResultUtil.ok("添加成功");
        }else {
            return  ResultUtil.ok("系统出错,添加失败");
        }
    }
    //修改
    @PostMapping("/updateCompany")
    public ResultVO updateCompany(@RequestBody Company company) {

        Boolean b= companyService.updateById(company);
        if (b){
            return  ResultUtil.ok("修改成功");
        }else {
            return  ResultUtil.ok("系统出错,操作失败");
        }
    }
    //删除
    @PostMapping("/deleteCompany")
    public ResultVO deleteClassify(@RequestBody CompanyClassify companyClassify) {
        Boolean  b= companyService.removeById(companyClassify.getClassifyId());
        if (b){
            return  ResultUtil.ok("删除成功");
        }else {
            return  ResultUtil.ok("系统出错,操作失败");
        }
    }

    //查询
    @PostMapping("/selectCompanyList")
    public ResultVO selectCompanyList(@RequestBody PageForm pageForm) {
        QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if (!StringUtils.isEmpty(pageForm.getCondition())){
            queryWrapper.like("proposer_name",pageForm.getCondition());
        }
        if (!StringUtils.isEmpty(pageForm.getCondition())){
            queryWrapper.like("proposer_name",pageForm.getCondition());
        }
        PageHelper.startPage(pageForm.getCurrent(), pageForm.getSize());
        List<Company> companyClassifyList= companyService.list(queryWrapper);
        PageInfo<Company> pageInfo = new PageInfo<>(companyClassifyList);
        PageVO p= pageVO.getPageVO0(pageInfo);
        return ResultUtil.ok("查询成功",p);
    }

}
