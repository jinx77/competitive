package com.xinzuo.competitive.util;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * RandomDigit:随机数工具类
 * @author zhangxiaoxiang
 * @date 2019/5/31 0031
 */
public class RandomDigit {

	/**
	 * 生成6位随机数
	 */
	public static String getVerificationCode(){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();//定义变长字符串
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
	
	/**
	 * 生成5位随机数
	 */
	public static String getfiveVerificationCode(){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();//定义变长字符串
        for(int i=0;i<5;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    /**
     * 生成3位随机数
     */
    public static String getfiveVerificationCode3(){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();//定义变长字符串
        for(int i=0;i<3;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
	
	/**
	 * 生成8位随机数
	 */
	public static String getSixteen(){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();//定义变长字符串
        for(int i=0;i<16;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    /**
     * 生成16位随机数
     */
    public static String getVerificationCodEeight(){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();//定义变长字符串
        for(int i=0;i<8;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
	
	/**
	 * 生成32位随机数
	 */
	public static String getVerificationCod32(){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();//定义变长字符串
        for(int i=0;i<32;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
	
	public static void main(String[] args) {
		System.out.println(getVerificationCod32());
	}

	/**
	 * 产生随机串部分代码：
	 */
    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
 
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";   
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();
        int number = 0;
        for (int i = 0; i < length; i++) {   
            number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
    }  


}
