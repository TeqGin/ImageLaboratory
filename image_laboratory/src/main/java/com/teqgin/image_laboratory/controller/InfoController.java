package com.teqgin.image_laboratory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class InfoController {

    /**
     * 进入网站相关信息页面
     * @return
     */
    @GetMapping("/about")
    public String about(){
        return "/introduction/about";
    }
}
