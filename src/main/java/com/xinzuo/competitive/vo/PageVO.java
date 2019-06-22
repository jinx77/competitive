package com.xinzuo.competitive.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 返回分页实体类
 * </p>
 *
 * @author jc
 * @since 2019-06-04
 */
@Component
@Data
public class PageVO<T> {
    //当前页
    private Object current;
    //每页记录数
    private Object size;
    //总记录数
    private Long total;
    //返回数据列表
    private T dataList;
    //获取返回数据
     public PageVO getPageVO(IPage iPage){

         PageVO pageVO=new PageVO();
         pageVO.setCurrent(iPage.getCurrent());
         pageVO.setSize(iPage.getSize());
         pageVO.setTotal(iPage.getTotal());
         return pageVO;

     }

}
