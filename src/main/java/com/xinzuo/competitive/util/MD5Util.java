package com.xinzuo.competitive.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * MD5Util:MD5加密：MD5 + 盐 + 散列次数
 * @author zhangxiaoxiang
 * @date 2019/5/31 0031
 */
public class MD5Util {

    public static String getMD5Sign(String userPhone,String password){

        //加密：MD5 + 盐 + 散列次数
        Md5Hash  md5Hash=new Md5Hash(userPhone,password,3);
        return md5Hash.toString();
    }

    public static void main(String[] args) {
       System.out.println(getMD5Sign("jc","123"));
    }
}
