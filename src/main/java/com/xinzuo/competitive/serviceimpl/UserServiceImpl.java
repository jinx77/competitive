package com.xinzuo.competitive.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.form.UserForm;
import com.xinzuo.competitive.pojo.User;
import com.xinzuo.competitive.dao.UserDao;
import com.xinzuo.competitive.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinzuo.competitive.util.JwtUtil;
import com.xinzuo.competitive.util.MD5Util;
import com.xinzuo.competitive.util.MyBase64Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    @Autowired
    MyBase64Utils myBase64Utils;
    @Override
    public User userRegister(User user) {
        //密码处理后再比对(解密后比对也行)
       /* Map<String, Object> map = new HashMap<>();
        map.put("user_name", user.getUserName());*/
        String pwd = MD5Util.getMD5Sign(user.getUserName(), user.getUserPassword());
       /* map.put("user_password", pwd);*/

        QueryWrapper<User> userQueryWrapper =new QueryWrapper<>();
        userQueryWrapper.eq("user_name",user.getUserName());
        userQueryWrapper.eq("user_password",pwd);

        //判断账号和密码
        User user0 = userDao.selectOne(userQueryWrapper);
        //如果密码错误
        if (user0==null) {
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_name",user.getUserName());
            queryWrapper.eq("initial_password",pwd);
            user0 = userDao.selectOne(queryWrapper);
        }
        if (user0==null){
            throw new CompetitiveException("用户名或者密码错误!");
        }
        if (user0.getExpireTime()==null||user0.getExpireTime().getTime()-new Date().getTime()<0){
            throw new CompetitiveException("该系统已超过使用期限,请联系管理员处理");
        }
        User u = user0;
        //使用加密后的密码生成token
        String token = JwtUtil.sign(user.getUserName(), pwd);
        //密码不回显
        u.setUserPassword(token);
        return u;


    }

    @Override
    public int userChange(UserForm userForm) {
        System.out.println(userForm.getUserId()+".....");


      User user= userDao.selectById(userForm.getUserId());

      if (user==null){
          throw new CompetitiveException("账号丢失了 请联系管理员");
      }
        String  initialPassword= MD5Util.getMD5Sign(user.getUserName(),userForm.getInitialPassword());
        if (!user.getInitialPassword().equals(initialPassword)){
            throw new CompetitiveException("初始密码错误");

        }
        User u=new User();
        u.setUserId(user.getUserId());
        if (!userForm.getUserPassword().equals("")&&userForm.getLogoIcon()!=null){
            String  userPassword=MD5Util.getMD5Sign(user.getUserName(), userForm.getUserPassword());
            u.setUserPassword(userPassword);
        }
        if ((!userForm.getLogoIcon().equals(""))&&userForm.getLogoIcon()!=null){
          String url=  myBase64Utils.UploadImage(userForm.getLogoIcon());
          u.setLogoIcon(url);
        }
        u.setTitleName(userForm.getTitleName());
        int i= userDao.updateById(u);
        return i;

    }
}
