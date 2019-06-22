package com.xinzuo.competitive.serviceimpl;

import com.xinzuo.competitive.pojo.User;
import com.xinzuo.competitive.dao.UserDao;
import com.xinzuo.competitive.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
