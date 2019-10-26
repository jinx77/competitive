package com.xinzuo.competitive.controller;


import com.xinzuo.competitive.service.DepositService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
    public synchronized ResultVO readExcel(MultipartFile file, @RequestParam String projectsId) {
        depositService.readExcel(file,projectsId);

        return ResultUtil.ok("导入成功.");
    }

    //下载模板
    @RequestMapping("download")
    public void download(HttpServletResponse response) throws Exception{
        String fileName = "保证金缴纳名单导入模板.xls";
        InputStream is = ClassUtils.class.getResourceAsStream("/static/excelmb/保证金缴纳名单导入模板.xls");
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(), "ISO-8859-1"));
        OutputStream os= response.getOutputStream();
        byte[] b=new byte[1024];
        int len;
        while ((len=is.read(b))!=-1){
            os.write(b, 0, len);
        }
        is.close();
        os.close();
    }

}
