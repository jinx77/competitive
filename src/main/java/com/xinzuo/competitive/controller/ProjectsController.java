package com.xinzuo.competitive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinzuo.competitive.form.PageForm;
import com.xinzuo.competitive.pojo.Projects;
import com.xinzuo.competitive.service.ProjectsService;
import com.xinzuo.competitive.util.KeyUtil;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.PageVO;
import com.xinzuo.competitive.vo.ProjectsVO;
import com.xinzuo.competitive.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@Slf4j
@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Autowired
    ProjectsService projectsService;
    @Autowired
    PageVO pageVO;
    //新建项目
    @PostMapping("/addProjects")
    public ResultVO addProjects(@RequestBody Projects projects){
        projects.setProjectsId(KeyUtil.genUniqueKey());
        projects.setCreateTime(new Date());
       Boolean b= projectsService.save(projects);
       if (b){
             return ResultUtil.ok("新建项目成功");
       }else {
           return ResultUtil.no("操作失败");
       }
    }
    //显示所有项目列表
    @PostMapping("selectProjectsList")
    public ResultVO selectProjectsList(PageForm pageForm) {
        QueryWrapper<Projects> queryWrapper = new QueryWrapper<>();
        if (pageForm.getCondition()!=null&&pageForm.getCondition()!=""){
            log.info("-----"+pageForm.getCondition());
            queryWrapper.like("projects_name",pageForm.getCondition());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<Projects> iPage = projectsService.page(new Page<Projects>(pageForm.getCurrent(), pageForm.getSize()), queryWrapper);
        List<Projects> projectsList=iPage.getRecords();
        List<ProjectsVO> projectsVOList=new ArrayList<>();
        BeanUtils.copyProperties(projectsList,projectsVOList);
        projectsList.forEach(projects -> {
            ProjectsVO projectsVO=new ProjectsVO();
            BeanUtils.copyProperties(projects,projectsVO);
            projectsVOList.add(projectsVO);
        });
        PageVO p=pageVO.getPageVO(iPage);
        p.setDataList(projectsVOList);
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
        Boolean b= projectsService.removeById(projects.getProjectsId());
        if (b){
            return ResultUtil.ok("删除成功");
        }
        else {
            return ResultUtil.no("id错误,操作失败");
        }
    }
}
