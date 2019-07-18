package com.xinzuo.competitive.handler;

import com.xinzuo.competitive.exception.CompetitiveException;
import com.xinzuo.competitive.util.ResultUtil;
import com.xinzuo.competitive.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

/**
 * Created by jc
 * 2019-05-21 22:07
 */
@Slf4j
@ControllerAdvice
public class CompetitiveExceptionHandler {

    //拦截自定义异常
    @ExceptionHandler(value = CompetitiveException.class)
    @ResponseBody
    public ResultVO handlerLvyouException(CompetitiveException e) {
        log.info("自定义异常-->"+e.getMessage());
        return ResultUtil.no(e.getMessage());
    }

    //拦截身份认证异常/user/loginbyauthorized
   /* @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public ResultVO handlerAuthenticationException(AuthenticationException e) {
        return ResultUtil.no(e.getMessage());
    }*/
    //拦截系统异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handlerException(Exception e) {
        log.error("系统异常-->"+e.getMessage());
        return ResultUtil.no("系统异常");
    }
}
