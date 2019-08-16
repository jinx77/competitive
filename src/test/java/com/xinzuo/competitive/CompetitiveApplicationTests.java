package com.xinzuo.competitive;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitiveApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void getPass() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ0");
        String url = textEncryptor.encrypt("jdbc:mysql://localhost:3306/competitive?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowMultiQueries=true");
        String name = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("xiaowan123");
//解密内容
//        String url = textEncryptor.decrypt("");
//        String name = textEncryptor.decrypt("");
//        String password = textEncryptor.decrypt("4EyN0xDLbnP2lsaayjl8fbIctj5bVIdD");
        System.out.println(url + "----------------");
        System.out.println(name + "----------------");
        System.out.println(password + "----------------");
        Assert.assertTrue(name.length() > 0);
        Assert.assertTrue(password.length() > 0);
    }

}
