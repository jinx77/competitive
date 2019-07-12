package com.xinzuo.competitive.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * MyBase64Utils:BASE64字符串 图片上传工具类
 * @author jc
 * @date 2019/7/12
 */
@Slf4j
@Component
public class MyBase64Utils {

    //base64：BASE64字符串
    public  String UploadImage(String base64) {
        System.out.println("---进入图片上传工具类");
        if (base64 == null) {
            return null;
        } else {
            System.out.println("---正在准备上传图片");
            //base64解码对象创建
            BASE64Decoder base64Decoder = new BASE64Decoder();
            //String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            //生成唯一图片名称
            String uuidKey = UUID.randomUUID().toString().replace("-", "") + ".";
            //设置图片上传路径
            String webappRoot = null;
            File path =null;
            //解决不同系统下路径问题
            String s = File.separator;
            File upload=null;
            //获取跟目录
            try {
                //获取项目的根路径
                path= new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists()) path = new File("");
                System.out.println("path:"+path.getAbsolutePath());
                upload = new File(path.getAbsolutePath(),"static/images/");
                if(!upload.exists()) upload.mkdirs();
                System.out.println("upload url:"+upload.getAbsolutePath());
                System.out.println("path:" + path.getAbsolutePath());
            } catch (Exception e) {
                e.getMessage();
            }
            //截取上传图片类型
            String imgetype = base64.substring(11, 15);
            String jc = null;
            if (imgetype.equals("jpeg")) {
                jc = "jpeg";
                imgetype = "jpg";
            } else {
                imgetype = "png";
                jc = "png";
            }
            String imgpath = upload+s+  uuidKey + imgetype;
            String imgUrl="/user/image?image="+ uuidKey + imgetype;
            log.info("imgpath------"+imgpath);
            //获取项目图片访问路径
           // String imageName = s + uuidKey + imgetype;
            try {
                //去除base64字符串不需要的头部字符串.
                //String base=base64.replace("data:image/"+jc+";base64,","");
                String base = base64.replace("data:image/" + jc + ";base64,", "");
               // System.out.println("1111111111"+base);
                //解码
                byte[] b = base64Decoder.decodeBuffer(base);
                for (int i = 0; i < b.length; ++i){
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                FileOutputStream fileOutputStream = new FileOutputStream(imgpath);
                fileOutputStream.write(b);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {
                return null;
            }
          /*  String imageUrl = s + "images" + imageName;
            System.out.println(webappRoot + imageUrl);
            System.out.println("图片上传的位置----" + imageUrl);*/
         // File f=new File(imgpath);
          log.info("本地地址"+imgUrl);
          // String cc= "https://xinzuolvyou.oss-cn-beijing.aliyuncs.com/"+aliyunOSSUtil.upload(f);
            return imgUrl;
        }
    }
}