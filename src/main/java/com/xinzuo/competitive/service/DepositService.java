package com.xinzuo.competitive.service;

import com.xinzuo.competitive.pojo.Deposit;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 保证金表 服务类
 * </p>
 *
 * @author jc
 * @since 2019-06-21
 */
public interface DepositService extends IService<Deposit> {
   int readExcel(MultipartFile excel, String projectsId);
}
