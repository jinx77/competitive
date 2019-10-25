package com.xinzuo.competitive.controller;

import com.sun.javafx.collections.MappingChange;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/jc")
public class TestController {
    @RequestMapping("upload")
    public Map upload(MultipartFile file) throws Exception{
        System.out.println(file.getName());
        File path=new File(ResourceUtils.getURL("classpath:").getPath());
        File upload = new File(path.getAbsolutePath(),"static/images/upload/");
        System.out.println(upload.exists());
        if(!upload.exists())
            upload.mkdirs();
        InputStream inputStream=file.getInputStream();
        int len;
        byte[] bs = new byte[1024];
        OutputStream os=new FileOutputStream(upload+"/"+file.getOriginalFilename());
        while ((len = inputStream.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        inputStream.close();
        os.close();
        Map<String,String> map= new HashMap();
        map.put("code","200");
        return map;
    }

   /* @RequestMapping("download")
    public void download(HttpServletResponse response) throws Exception{
        String fileName = "班级数据导入模板.xlsx";
        File path=new File(ResourceUtils.getURL("classpath:").getPath());
        File upload = new File(path.getAbsolutePath(),"static/images/upload/班级数据导入模板.xlsx");
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(), "ISO-8859-1"));
        InputStream is=new FileInputStream(upload);
      OutputStream os= response.getOutputStream();
      byte[] b=new byte[1024];
      int len;
      while ((len=is.read(b))!=-1){
          os.write(b, 0, len);
      }
        is.close();
        os.close();
    }*/
}
