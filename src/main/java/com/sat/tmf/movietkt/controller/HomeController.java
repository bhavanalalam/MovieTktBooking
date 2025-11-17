package com.sat.tmf.movietkt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", " Welcome to TMF Movie Ticket App with Spring Boot MVC");
        return "home";
    }
}
