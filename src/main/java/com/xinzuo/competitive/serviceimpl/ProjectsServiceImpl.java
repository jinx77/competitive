package com.xinzuo.competitive.serviceimpl;

import com.xinzuo.competitive.pojo.Projects;
import com.xinzuo.competitive.dao.ProjectsDao;
import com.xinzuo.competitive.service.ProjectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
@Service
public class ProjectsServiceImpl extends ServiceImpl<ProjectsDao, Projects> implements ProjectsService {

}
