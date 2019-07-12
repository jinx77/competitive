package com.xinzuo.competitive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.Company;
import com.xinzuo.competitive.pojo.CompanyClassify;
import com.xinzuo.competitive.service.CompanyClassifyService;
import com.xinzuo.competitive.service.CompanyService;
import com.xinzuo.competitive.util.KeyUtil;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.CompanyVO;
import com.xinzuo.competitive.vo.PageVO;
import com.xinzuo.competitive.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.Kernel;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 企业信息表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-07-09
 */
@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyClassifyService companyClassifyService;
    @Autowired
    PageVO pageVO;
    //手动添加企业
    @PostMapping("/addCompany")
    public ResultVO addCompany(@RequestBody Company company) {
       company.setCompanyId(KeyUtil.genUniqueKey());
       company.setCreateTime(new Date());
        QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("proposer_name",company.getProposerName());
        if ( companyService.getOne(queryWrapper)!=null){
            return  ResultUtil.no("该公司已存在");
        }

        Boolean b= companyService.save(company);
        if (b){
            return  ResultUtil.ok("添加成功");
        }else {
            return  ResultUtil.no("系统出错,添加失败");
        }
    }

    //导入Excel表
    @PostMapping("/readExcel")
    public ResultVO readExcel(MultipartFile excel, @RequestParam int companyClassifyId) {
        companyService.readExcel(excel,companyClassifyId);
        return ResultUtil.ok("导入成功");
    }
    //修改
    @PostMapping("/updateCompany")
    public ResultVO updateCompany(@RequestBody Company company) {
        QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("proposer_name",company.getProposerName());
        Company c= companyService.getOne(queryWrapper);
        if ( companyService.getOne(queryWrapper)!=null){
            if (!company.getCompanyId().equals(c.getCompanyId())){
                return  ResultUtil.no("该公司已存在");
            }
        }
        Boolean b= companyService.updateById(company);
        if (b){
            return  ResultUtil.ok("修改成功");
        }else {
            return  ResultUtil.ok("系统出错,操作失败");
        }
    }
    //删除
    @PostMapping("/deleteCompany")
    public ResultVO deleteClassify(@RequestBody Company company) {
        Boolean  b= companyService.removeById(company.getCompanyId());
        if (b){
            return  ResultUtil.ok("删除成功");
        }else {
            return  ResultUtil.no("系统出错,操作失败");
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
        if (!StringUtils.isEmpty(pageForm.getCompanyClassifyId())){
            queryWrapper.eq("company_classify_id",pageForm.getCompanyClassifyId());
        }
        PageHelper.startPage(pageForm.getCurrent(), pageForm.getSize());
        List<Company> companyClassifyList= companyService.list(queryWrapper);
        PageInfo<Company> pageInfo = new PageInfo<>(companyClassifyList);
        PageVO p= pageVO.getPageVO0(pageInfo);
        List<CompanyVO> companyVOList=new CopyOnWriteArrayList<>();
        pageInfo.getList().forEach(company -> {
           CompanyClassify companyClassify= companyClassifyService.getById(company.getCompanyClassifyId());
            CompanyVO companyVO=new CompanyVO();
            BeanUtils.copyProperties(company,companyVO);
            if (companyClassify!=null){

                companyVO.setCompanyClassifyName(companyClassify.getClassifyName());
            }

            companyVOList.add(companyVO);
        });
        p.setDataList(companyVOList);
        return ResultUtil.ok("查询成功",p);
    }

}
