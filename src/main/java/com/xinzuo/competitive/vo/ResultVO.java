package com.xinzuo.competitive.vo;

import lombok.Data;


/**
 * ResultVO:http 请求返回的最外层对象
 *
 * @author zhangxiaoxiang
 * @date: 2019/05/23
 */
@Data
public class ResultVO<T>{
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


}
