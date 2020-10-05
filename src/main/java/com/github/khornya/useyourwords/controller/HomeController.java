package com.github.khornya.useyourwords.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String home() {
        return "home";
    }
}
