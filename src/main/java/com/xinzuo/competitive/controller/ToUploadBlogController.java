package com.xinzuo.competitive.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oss")
@Slf4j
@CrossOrigin
public class ToUploadBlogController {

    /**
     * 跳转到文件上传页
     *
     * @return
     */
    @GetMapping("/toUploadBlog")
    public String toUploadBlog() {
        return "oss/upload";
    }
}
