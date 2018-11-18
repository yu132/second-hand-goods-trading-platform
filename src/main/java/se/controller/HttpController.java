package se.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping(value = "/main")
public class HttpController {

@RequestMapping(value = "/register")
    
public String register() {
        return "register";
    }
}
