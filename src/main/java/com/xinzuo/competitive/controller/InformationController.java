package com.xinzuo.competitive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.excel.ExcelUtil;
import com.xinzuo.competitive.excel.pojo.InformationDB;
import com.xinzuo.competitive.pojo.Information;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.InformationService;
import com.xinzuo.competitive.service.ProjectsQualificationService;
import com.xinzuo.competitive.service.ProjectsService;
import com.xinzuo.competitive.service.QualificationService;
import com.xinzuo.competitive.util.KeyUtil;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 承包商信息库表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/information")
public class InformationController {
    @Autowired
    InformationService informationService;
    //导入Excel表
    @PostMapping("/readExcel")
    public ResultVO readExcel(MultipartFile excel,@RequestParam String projectsId) {
        informationService.readExcel(excel,projectsId);

        return ResultUtil.ok("导入成功");
    }
}
