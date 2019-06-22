package com.xinzuo.competitive.util;

import java.util.Random;

/**
 * KeyUtil:生成的唯一主键 格式：时间+随机数
 *
 * @author zhangxiaoxiang
 * @date 2019/5/31 0031
 */

public class KeyUtil {


    /**
     * 生成的唯一主键 格式：时间+随机数
     * @return
     */

    public static String genUniqueKey() {
        Random random = new Random();

        Integer a = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(a);

    }
}
