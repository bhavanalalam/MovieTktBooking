package com.sat.tmf.movietkt.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sat.tmf.movietkt.entities.Refund;
import com.sat.tmf.movietkt.entities.User;
import com.sat.tmf.movietkt.service.RefundService;
import com.sat.tmf.movietkt.service.UserService;

@Controller
@RequestMapping("/refunds")
public class RefundController {

    @Autowired
    private RefundService refundService;

    @Autowired
    private UserService userService;

    // User view
    @GetMapping
    public String userRefunds(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Refund> refunds = refundService.getRefundsForUser(user);
        model.addAttribute("refunds", refunds);
        model.addAttribute("contentPage", "/WEB-INF/views/user/userRefunds.jsp");
        model.addAttribute("pageTitle", "My Refunds");
        return "layout/layout";
    }

    // Admin view
    @GetMapping("/admin")
    public String adminRefunds(Model model) {
        List<Refund> refunds = refundService.getAllRefunds();
        model.addAttribute("refunds", refunds);
        model.addAttribute("contentPage", "/WEB-INF/views/admin/adminRefunds.jsp");
        model.addAttribute("pageTitle", "Refund Management");
        return "layout/layout";
    }
}

