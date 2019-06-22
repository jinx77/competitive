package com.xinzuo.competitive.util;


import com.xinzuo.competitive.vo.ResultVO;

/**
 * ResultUtil:返回给前端数据封装工具类
 *
 * @author zhangxiaoxiang
 * @date: 2019/05/23
 */
public class ResultUtil {


    /**
     * 成功
     * @param msg
     * @return
     */
    public static ResultVO ok(String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg(msg);
        resultVO.setData(null);
        return resultVO;
    }
    public static ResultVO ok(String msg,Object data){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg(msg);
        resultVO.setData(data);
        return resultVO;
    }

    /**
     * 失败情况
     * @param msg
     * @return
     */
    public static ResultVO no(String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(500);
        resultVO.setMsg(msg);
        resultVO.setData(null);
        return resultVO;
    }

    /**
     * 身份验证失败
     * @param msg
     * @return
     */
    public static ResultVO noVerify(String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(401);
        resultVO.setMsg(msg);
        resultVO.setData(null);
        return resultVO;
    }


}
