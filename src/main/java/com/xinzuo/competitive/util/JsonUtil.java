package com.xinzuo.competitive.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JsonUtil:转换为json工具类
 * @author zhangxiaoxiang
 * @date 2019/5/31 0031
 */

public class JsonUtil {

    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
