package com.sat.tmf.movietkt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sat.tmf.movietkt.entities.User;
import com.sat.tmf.movietkt.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // =================== REGISTER ===================
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("contentPage", "/WEB-INF/views/register.jsp");
        model.addAttribute("pageTitle", "Register");
        return "layout/layout";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.register(user);
            model.addAttribute("message", "Registration successful! You can now log in.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("contentPage", "/WEB-INF/views/register.jsp");
        return "layout/layout";
    }

    // =================== PROFILE ===================
    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("contentPage", "/WEB-INF/views/profile.jsp");
        model.addAttribute("pageTitle", "My Profile");
        return "layout/layout";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute User user, Principal principal, Model model) {
        userService.updateUserProfile(principal.getName(), user);
        model.addAttribute("message", "Profile updated successfully!");
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("contentPage", "/WEB-INF/views/profile.jsp");
        return "layout/layout";
    }

    // =================== ADMIN VIEW ALL USERS ===================
    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("contentPage", "/WEB-INF/views/admin/adminUsers.jsp");
        model.addAttribute("pageTitle", "All Users");
        return "layout/layout";
    }
}

