package com.xinzuo.competitive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinzuo.competitive.dao.InformationDao;
import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.Projects;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.DepositService;
import com.xinzuo.competitive.service.InformationService;
import com.xinzuo.competitive.service.ProjectsService;
import com.xinzuo.competitive.service.QualificationService;
import com.xinzuo.competitive.util.KeyUtil;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Autowired
    ProjectsService projectsService;
    @Autowired
    PageVO pageVO;
    @Autowired
    DepositService depositService;
    @Autowired
    InformationService informationService;
    @Autowired
    QualificationService qualificationService;
    //新建项目
    @PostMapping("/addProjects")
    public ResultVO addProjects(@RequestBody Projects projects){
        String projectsId=KeyUtil.genUniqueKey();
        projects.setProjectsId(projectsId);
        projects.setCreateTime(new Date());
        projects.setWinQuantity(0);
       Boolean b= projectsService.save(projects);
       if (b){
             return ResultUtil.ok("新建项目成功",projectsId);
       }else {
           return ResultUtil.no("操作失败");
       }
    }
    //显示所有项目列表
    @PostMapping("selectProjectsList")
    public ResultVO selectProjectsList(@RequestBody PageForm pageForm) {
        QueryWrapper<Projects> queryWrapper = new QueryWrapper<>();

        if (pageForm.getCondition()!=null&&pageForm.getCondition()!=""){
            log.info("-----"+pageForm.getCondition());
            queryWrapper.like("projects_name",pageForm.getCondition());
        }

        if (pageForm.getProjectsId()!=null&&pageForm.getProjectsId()!=""){
            log.info("-----"+pageForm.getCondition());
            queryWrapper.eq("projects_id",pageForm.getProjectsId());
        }

            log.info(pageForm.getSelectType()+"----------");
        if (pageForm.getSelectType()!=null) {
                //查询已竞标公司
                if (pageForm.getSelectType() == 1) {
                    queryWrapper.gt("win_quantity", 0);
                }    //查询可以竞标项目
                if (pageForm.getSelectType() == 2) {

                    //queryWrapper.exists("select *from projects where win_quantity!=bidding_quantity");
                }
                //查询未竞标项目
                if (pageForm.getSelectType() == 3) {
                    queryWrapper.eq("win_quantity", 0);
                }
        }
        queryWrapper.orderByDesc("create_time");


        PageHelper.startPage(pageForm.getCurrent(), pageForm.getSize());
        List<Projects> qualificationList= projectsService.list(queryWrapper);
        PageInfo<Projects> pageInfo = new PageInfo<Projects>(qualificationList);
       System.out.println( pageInfo.getTotal()+"----------===========");

        IPage<Projects> iPage = projectsService.page(new Page<Projects>(pageForm.getCurrent(), pageForm.getSize()), queryWrapper);
        //List<Projects> projectsList=iPage.getRecords();
        List<Projects> projectsList=pageInfo.getList();
        List<ProjectsVO> projectsVOList=new ArrayList<>();
        BeanUtils.copyProperties(projectsList,projectsVOList);
        System.out.println(iPage.getPages()+"5555555555");
        projectsList.forEach(projects -> {
            ProjectsVO projectsVO=new ProjectsVO();
            BeanUtils.copyProperties(projects,projectsVO);
            //统计数量
            projectsVO.setImaginaryQuantity(qualificationService.selectQualificationa(projects.getProjectsId()));
            projectsVO.setActualQuantity(qualificationService.selectQualificationb(projects.getProjectsId()));
            projectsVOList.add(projectsVO);
        });
        //PageVO p=pageVO.getPageVO(iPage);
        PageVO p=new PageVO();
        p.setDataList(projectsVOList);
        p.setTotal(pageInfo.getTotal());
        p.setCurrent(pageInfo.getPageNum());
        p.setSize(pageInfo.getPageSize());
        return ResultUtil.ok("显示列表成功", p);
    }
    //修改项目
    @PostMapping("/updateProjects")
    public ResultVO updateProjects(@RequestBody Projects projects){
        if (projects.getProjectsId()==null||projects.getProjectsId()==""){
            return ResultUtil.no("projectsId--不能为空");
        }
      Boolean b= projectsService.updateById(projects);
      if (b){
         return ResultUtil.ok("修改成功");
      }
      else {
         return ResultUtil.no("id错误,操作失败");
      }
    }

    //删除项目
    @PostMapping("/deleteProjects")
    public ResultVO deleteProjects(@RequestBody Projects projects){
        if (projects.getProjectsId()==null||projects.getProjectsId()==""){
            return ResultUtil.no("projectsId--不能为空--");
        }
        int i= projectsService.deleteProjects(projects.getProjectsId());
        if (i>0){
            return ResultUtil.ok("删除成功");
        }
        else {
            return ResultUtil.no("id错误,操作失败");
        }
    }

    //打印结果
    @PostMapping("/print")
    public ResultVO print(@RequestBody Projects projects) {
       Projects p= projectsService.getById(projects.getProjectsId());
        if(p==null){
            return ResultUtil.no("项目ID错误,没有找到该项目");
        }
        PrintVO printVO=new PrintVO();
        BeanUtils.copyProperties(p,printVO);
        QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
        qualificationQueryWrapper.eq("Projects_id",projects.getProjectsId()).eq("win_status",1);
        List<Qualification> qualificationList=qualificationService.list(qualificationQueryWrapper);
        List<WinVO> winVOList=new ArrayList<>();
        qualificationList.forEach(qualification -> {
            WinVO winVO=new WinVO();
            BeanUtils.copyProperties(qualification,winVO);
            winVOList.add(winVO);
        });


        printVO.setWinVOList(winVOList);
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy年MM月dd日");

        // SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        String s= sdf1.format( p.getWinTime());
        System.out.println(sdf1.format( p.getWinTime()));
        printVO.setWinTime(s);
        return ResultUtil.ok("打印数据返回成功",printVO);
    }
}
