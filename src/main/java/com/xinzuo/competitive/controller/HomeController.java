package com.xinzuo.competitive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "jx777")
    public String homePage() {
        return "jx/login";
    }
}
