package com.sat.tmf.movietkt.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sat.tmf.movietkt.entities.Booking;
import com.sat.tmf.movietkt.entities.Show;
import com.sat.tmf.movietkt.entities.User;
import com.sat.tmf.movietkt.service.BookingService;
import com.sat.tmf.movietkt.service.ShowService;
import com.sat.tmf.movietkt.service.TemplateSeatService;
import com.sat.tmf.movietkt.service.UserService;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private ShowService showService;
    @Autowired private TemplateSeatService seatService;
    @Autowired private UserService userService;

    // View seat map for a show
    @GetMapping("/select/{showId}")
    public String selectSeats(@PathVariable Integer showId, Model model) {
        Show show = showService.findById(showId);
        model.addAttribute("show", show);
        model.addAttribute("templateSeats", seatService.findByTemplateId(show.getSeatTemplate().getId()));
        model.addAttribute("contentPage", "/WEB-INF/views/user/selectSeats.jsp");
        model.addAttribute("pageTitle", "Select Seats for " + show.getMovie().getTitle());
        return "layout/layout";
    }

    // Hold seats
    @PostMapping("/hold")
    public String holdSeats(@RequestParam Integer showId,
                            @RequestParam List<Integer> seatIds,
                            Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Show show = showService.findById(showId);
        Booking booking = bookingService.holdSeats(user, show, seatIds);
        model.addAttribute("booking", booking);
        model.addAttribute("contentPage", "/WEB-INF/views/user/confirmBooking.jsp");
        model.addAttribute("pageTitle", "Confirm Booking");
        return "layout/layout";
    }

    // Confirm booking
    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam Integer bookingId, Model model) {
        Booking booking = bookingService.confirmBooking(bookingId);
        model.addAttribute("booking", booking);
        model.addAttribute("contentPage", "/WEB-INF/views/user/bookingSuccess.jsp");
        model.addAttribute("pageTitle", "Booking Confirmed");
        return "layout/layout";
    }

    // View booking history
    @GetMapping("/history")
    public String viewHistory(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        List<Booking> bookings = bookingService.findByUser(user);
        model.addAttribute("bookings", bookings);
        model.addAttribute("contentPage", "/WEB-INF/views/user/bookingHistory.jsp");
        model.addAttribute("pageTitle", "My Bookings");
        return "layout/layout";
    }
}

