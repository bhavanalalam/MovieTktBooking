package com.sat.tmf.movietkt.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sat.tmf.movietkt.entities.Booking;
import com.sat.tmf.movietkt.entities.Payment;
import com.sat.tmf.movietkt.entities.User;
import com.sat.tmf.movietkt.service.BookingService;
import com.sat.tmf.movietkt.service.PaymentService;
import com.sat.tmf.movietkt.service.UserService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired private PaymentService paymentService;
    @Autowired private BookingService bookingService;
    @Autowired private UserService userService;

    // Initiate payment
    @GetMapping("/start/{bookingId}")
    public String startPayment(@PathVariable Integer bookingId, Principal principal, Model model) {
        Booking booking = bookingService.findById(bookingId);
        User user = userService.findByUsername(principal.getName());
        Payment payment = paymentService.initiatePayment(booking, user, "RAZORPAY");

        // In production: redirect to Razorpay checkout page
        model.addAttribute("payment", payment);
        model.addAttribute("booking", booking);
        model.addAttribute("contentPage", "/WEB-INF/views/user/paymentPage.jsp");
        model.addAttribute("pageTitle", "Make Payment");
        return "layout/layout";
    }

    // Razorpay or Stripe webhook (callback)
    @PostMapping("/webhook")
    @ResponseBody
    public String handleWebhook(@RequestBody String payload) {
        // Example payload parsing
        if (payload.contains("payment_success")) {
            String txnId = extractTxnId(payload);
            paymentService.handlePaymentSuccess(txnId, "RAZORPAY");
        } else if (payload.contains("payment_failed")) {
            String txnId = extractTxnId(payload);
            paymentService.handlePaymentFailure(txnId, "RAZORPAY");
        }
        return "OK";
    }

    private String extractTxnId(String payload) {
        // In real use, parse JSON from Razorpay/Stripe webhook payload
        int start = payload.indexOf("txn_");
        return start != -1 ? payload.substring(start, start + 36) : "unknown";
    }
}

