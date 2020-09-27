package com.wenqicode.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Wenqi Liang
 * @date 2020/9/23
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
