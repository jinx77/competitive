package com.xinzuo.competitive.controller;

import com.xinzuo.competitive.service.InformationService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

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
    public synchronized ResultVO readExcel(MultipartFile excel,@RequestParam String projectsId) {
        informationService.readExcel(excel,projectsId);

        return ResultUtil.ok("导入成功");
    }
}
