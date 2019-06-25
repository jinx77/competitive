package com.xinzuo.competitive.shiro;


import com.alibaba.fastjson.JSON;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.jwt.JwtToken;
import com.xinzuo.competitive.pojo.User;
import com.xinzuo.competitive.service.UserService;
import com.xinzuo.competitive.util.JsonUtil;
import com.xinzuo.competitive.util.JwtUtil;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author jc
 * @create 2018-07-12 15:23
 * @desc
 **/
@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {


    @Autowired
    UserService userService;


    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;

    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {


        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = JwtUtil.getUsername(principals.toString());
        //根据用户名查询权限
      /*  List<Resource> resourceList = adminUserService.selectResource(username);
        resourceList.parallelStream().forEach(resource ->
                simpleAuthorizationInfo.addStringPermission(resource.getResourceName())
        );*/
        return simpleAuthorizationInfo;

    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        /*//获取请求的相关属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        //写出之前解决乱码
        //解决 ??? 编码形式乱码
        response.setCharacterEncoding("UTF-8");
        //响应json格式
        response.setContentType("text/json");
*/
        String token = (String) auth.getCredentials();
        try {
            //注意修改了编码之后再获取流对象
           // PrintWriter printWriter = response.getWriter();
            // 解密获得username，用于和数据库进行对比
            String username = JwtUtil.getUsername(token);
            log.info("jwt验证用户名非空" + username);
            if (username == null) {
                //拦截住了返回json格式的响应
            /*    ResultVO resultVO = ResultUtil.noVerify("用户名为空导致验证失败!");
                printWriter.write(JSON.toJSONString(resultVO));
                printWriter.close();*/
            throw new AuthenticationException("验证失败");
            }
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_name",username);
            User userBean = userService.getOne(queryWrapper);
            log.info("查询数据库进一步验证用户" + JsonUtil.toJson(userBean));
            if (userBean == null) {
                //拦截住了返回json格式的响应
              /*  ResultVO resultVO = ResultUtil.noVerify("用户不存在导致验证失败!");
                printWriter.write(JSON.toJSONString(resultVO));*/
                throw new AuthenticationException("验证失败");
            }
            log.info("查询数据库进一步验证用户名和密码" + JsonUtil.toJson(userBean));
            if (!JwtUtil.verify(token, username, userBean.getUserPassword())) {
                //拦截住了返回json格式的响应
             /*   ResultVO resultVO = ResultUtil.noVerify("用户名称或者密码输入错误导致验证失败!");
                printWriter.write(JSON.toJSONString(resultVO));*/
                throw new AuthenticationException("验证失败");
            }
            log.info("验证成功");
            return new SimpleAuthenticationInfo(token, token, "my_realm");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

}

