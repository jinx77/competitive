package com.xinzuo.competitive.controller;

import com.xinzuo.competitive.service.InformationService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
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
    public synchronized ResultVO readExcel(MultipartFile file,@RequestParam String projectsId) {
        informationService.readExcel(file,projectsId);

        return ResultUtil.ok("导入成功");
    }

    //下载模板
    @RequestMapping("download")
    public void download(HttpServletResponse response) throws Exception{
        String fileName = "库名单导入模板.xls";
        InputStream is = ClassUtils.class.getResourceAsStream("/static/excelmb/库名单导入模板.xls");
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

    //下载模板
    @RequestMapping("zbdl")
    public void zbdl(HttpServletResponse response) throws Exception{
        String fileName = "招标代理名单导入模板.xlsx";
        InputStream is = ClassUtils.class.getResourceAsStream("/static/excelmb/招标代理名单导入模板.xlsx");
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
