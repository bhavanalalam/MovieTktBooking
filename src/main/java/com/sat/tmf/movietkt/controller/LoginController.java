package com.sat.tmf.movietkt.controller;

import com.sat.tmf.movietkt.entities.User;
import com.sat.tmf.movietkt.service.MovieService;
import com.sat.tmf.movietkt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;
    
    @Autowired 
    private MovieService movieService;

    // =================== LOGIN PAGE ===================
    @GetMapping
    public String showLoginPage(Model model) {
        model.addAttribute("pageTitle", "Login");
        model.addAttribute("contentPage", "/WEB-INF/views/login.jsp");
        return "layout/layout";
    }

    // =================== LOGIN SUBMIT ===================
    @PostMapping
    public String processLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        // Fetch user by username
        User user = userService.findByUsername(username);

        // Validate user credentials
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            model.addAttribute("message", "Welcome, " + user.getUsername() + "!");
            model.addAttribute("pageTitle", "Movies");
            model.addAttribute("movies",movieService.findAllMovies());    
            model.addAttribute("contentPage", "/WEB-INF/views/movies.jsp");
            return "layout/layout";
        } else {
            model.addAttribute("error", "Invalid username or password!");
            model.addAttribute("pageTitle", "Login");
            model.addAttribute("contentPage", "/WEB-INF/views/login.jsp");
            return "layout/layout";
        }
    }

    // =================== LOGOUT ===================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
