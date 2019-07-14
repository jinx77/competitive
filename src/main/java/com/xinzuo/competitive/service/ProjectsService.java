package com.xinzuo.competitive.service;

import com.xinzuo.competitive.dao.ProjectsDao;
import com.xinzuo.competitive.pojo.Projects;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
public interface ProjectsService extends IService<Projects> {

   int deleteProjects(String projectsId);

}
