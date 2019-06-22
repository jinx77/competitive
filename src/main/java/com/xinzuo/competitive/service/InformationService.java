package com.xinzuo.competitive.service;

import com.xinzuo.competitive.pojo.Information;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 承包商信息库表 服务类
 * </p>
 *
 * @author jc
 * @since 2019-06-22
 */
public interface InformationService extends IService<Information> {


    int readExcel(MultipartFile excel, String projectsId);
}
