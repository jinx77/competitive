package com.xinzuo.competitive.controller;


import com.xinzuo.competitive.form.UserForm;
import com.xinzuo.competitive.pojo.User;
import com.xinzuo.competitive.service.UserService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.LogoVO;
import com.xinzuo.competitive.vo.ResultVO;
import com.xinzuo.competitive.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
        //登录
    @PostMapping("/register")
    public ResultVO userRegister(@RequestBody User loginUser) {
        if (loginUser.getUserName()==null||loginUser.getUserName()==""){
            return ResultUtil.no("请输入用户名");
        }
        if (loginUser.getUserPassword()==null||loginUser.getUserPassword()==""){
            return ResultUtil.no("请输入密码");
        }
      User u=  userService.userRegister(loginUser);
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(u,userVO);
      return ResultUtil.ok("登录成功",userVO);
    }

    //修改user信息
    @PostMapping("/change")
    public ResultVO userChange(@RequestBody UserForm userForm) {
       int i= userService.userChange(userForm);
       if (i>0){
       return ResultUtil.ok("修改成功");
       }
       return ResultUtil.no("修改失败");
    }

    //查询user信息
    @PostMapping("/selectUser")
    public ResultVO selectUser(@RequestBody UserForm userForm) {
       User user= userService.getById(userForm.getUserId());

       if (user!=null){
           UserVO userVO=new UserVO();
           BeanUtils.copyProperties(user,userVO);
           userVO.setUserPassword(null);
           return ResultUtil.ok("查询成功",userVO);
       }else {
           return ResultUtil.no("系统繁忙,请稍后再试");
       }
    }

    //显示logo
    @PostMapping("/selectLogo")
    public ResultVO selectLogo() {
        User user= userService.getById("0");

        if (user!=null){
            LogoVO logoVO=new LogoVO();
            BeanUtils.copyProperties(user,logoVO);
            return ResultUtil.ok("查询成功",logoVO);
        }else {
            return ResultUtil.no("系统繁忙,请稍后再试");
        }
    }

    //本地图片访问接口
    @RequestMapping(value = "/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@RequestParam String image) throws IOException {
        //获取跟目录
        File upload=null;
        try {
            //获取项目的根路径
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");
            System.out.println("path:"+path.getAbsolutePath());
         upload = new File(path.getAbsolutePath(),"static/images/");
            if(!upload.exists()) upload.mkdirs();
        } catch (Exception e) {

            e.getMessage();

        }
        String imgpath =upload.getPath() +"/"+ image;
        File file=new File(imgpath);
        if (!file.exists()){

            return null;
        }
        FileInputStream fileInputStream=new FileInputStream(new File(imgpath));
        byte[] b=new byte[fileInputStream.available()];
        fileInputStream.read(b,0,fileInputStream.available());
        fileInputStream.close();
        return b;
    }
}
