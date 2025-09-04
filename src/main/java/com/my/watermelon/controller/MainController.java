package com.my.watermelon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // src/main/resources/templates/login.html
    }
    @GetMapping("/home")
    public String home() {
        return "home"; // home.html
    }
}
