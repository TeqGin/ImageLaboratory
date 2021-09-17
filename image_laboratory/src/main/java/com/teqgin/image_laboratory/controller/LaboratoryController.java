package com.teqgin.image_laboratory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/laboratory")
public class LaboratoryController {
    @GetMapping("/edit")
    public String edit(){
        return "/laboratory/edit";
    }
}
