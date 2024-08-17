package pl.srb.srb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @GetMapping("user/home")
    public String home() {return "user/home";}

//    @GetMapping("user/reservationForm")
//    public String reservationForm() {return "user/reservationForm";}
}


