package com.xinzuo.competitive.controller;


import com.xinzuo.competitive.pojo.User;
import com.xinzuo.competitive.service.UserService;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
      return ResultUtil.ok("登录成功",u);
    }
}
