package com.xinzuo.competitive.serviceimpl;

import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.pojo.User;
import com.xinzuo.competitive.dao.UserDao;
import com.xinzuo.competitive.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.util.JwtUtil;
import com.xinzuo.competitive.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public User userRegister(User user) {
        //密码处理后再比对(解密后比对也行)
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", user.getUserName());
        String pwd = MD5Util.getMD5Sign(user.getUserName(), user.getUserPassword());
        map.put("user_password", pwd);
        //判断账号和密码
        List<User> users = userDao.selectByMap(map);
        //如果密码错误
        if (users.size() == 0) {
            throw new CompetitiveException("用户名或者密码错误!");
        }
        User u = users.get(0);
        //使用加密后的密码生成token
        String token = JwtUtil.sign(user.getUserName(), pwd);
        //密码不回显
        u.setUserPassword(token);
        return u;


    }
}
