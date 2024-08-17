package pl.srb.srb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //TODO poprzenosić metody do osbnych kontrolerów a nie w jednym

    @GetMapping("/")
    public String index() {
        return "public/index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "public/contact";
    }

    @GetMapping("/about")
    public String about() { return "public/about";}

    @GetMapping("/gallery")
    public String gallery() { return "public/gallery";}

    @GetMapping("/test")
    public String test() { return "/test";}



    @GetMapping("login")
    public String login() {return "public/login";}




}

