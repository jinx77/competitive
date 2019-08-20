package com.xinzuo.competitive;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xinzuo.competitive.dao")
public class CompetitiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompetitiveApplication.class, args);

        System.out.println("http://localhost:8081/oss/toUploadBlog");
        System.out.println("http://localhost:8081/jx/login.html");
        try {
            Runtime.getRuntime().exec("cmd   /c   start   http://localhost:8081/jx/login.html");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
