package com.sat.tmf.movietkt.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sat.tmf.movietkt.entities.Booking;
import com.sat.tmf.movietkt.entities.BookingSeat;
import com.sat.tmf.movietkt.entities.User;
import com.sat.tmf.movietkt.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromAddress;

    @Override
    public void sendBookingConfirmation(User user, Booking booking) {
        String seatList = booking.getSeats().stream()
                .map(BookingSeat::getSeatCode)
                .collect(Collectors.joining(", "));

        String subject = "🎟️ Booking Confirmed - " + booking.getShow().getMovie().getTitle();
        String body = "Dear " + user.getFullName() + ",\n\n"
                + "Your booking has been successfully confirmed!\n\n"
                + "🎬 Movie: " + booking.getShow().getMovie().getTitle() + "\n"
                + "🕒 Show Time: " + booking.getShow().getShowTime() + "\n"
                + "🏢 Theater: " + booking.getShow().getScreen().getTheater().getName() + "\n"
                + "💺 Seats: " + seatList + "\n"
                + "💰 Amount Paid: ₹" + booking.getAmount() + "\n\n"
                + "Thank you for booking with us!\n"
                + "— MovieTickets Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void sendPaymentFailure(User user, Booking booking) {
        String subject = "⚠️ Payment Failed - " + booking.getShow().getMovie().getTitle();
        String body = "Dear " + user.getFullName() + ",\n\n"
                + "Unfortunately, your payment for booking ID #" + booking.getId() + " has failed.\n"
                + "Please try again or contact support.\n\n"
                + "— MovieTickets Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}

