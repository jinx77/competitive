package com.xinzuo.competitive.service;

import com.xinzuo.competitive.form.UserForm;
import com.xinzuo.competitive.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
public interface UserService extends IService<User> {

    User userRegister(User user);

    int userChange(UserForm userForm);
}
