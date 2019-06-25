package com.xinzuo.competitive.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.service.DepositService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 保证金表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
@CrossOrigin
@RestController
@RequestMapping("/deposit")
public class DepositController {
    @Autowired
    DepositService depositService;
    //导入Excel表
    @PostMapping("/readExcel")
    public ResultVO readExcel(MultipartFile excel, @RequestParam String projectsId) {
        depositService.readExcel(excel,projectsId);

        return ResultUtil.ok("导入成功.");
    }



}
