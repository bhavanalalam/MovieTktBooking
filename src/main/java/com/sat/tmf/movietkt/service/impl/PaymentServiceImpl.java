package com.sat.tmf.movietkt.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sat.tmf.movietkt.dao.PaymentDao;
import com.sat.tmf.movietkt.entities.Booking;
import com.sat.tmf.movietkt.entities.Payment;
import com.sat.tmf.movietkt.entities.User;
import com.sat.tmf.movietkt.service.BookingService;
import com.sat.tmf.movietkt.service.EmailService;
import com.sat.tmf.movietkt.service.PaymentService;
import com.sat.tmf.movietkt.service.RefundService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired 
    private PaymentDao paymentDao;
    
    @Autowired 
    private BookingService bookingService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private RefundService refundService;

    @Override
    public Payment initiatePayment(Booking booking, User user, String gateway) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setUser(user);
        payment.setAmount(booking.getAmount());
        payment.setStatus("INITIATED");
        payment.setGateway(gateway);
        payment.setTxnId(UUID.randomUUID().toString()); // replace with Razorpay/Stripe txn id
        payment.setCreatedAt(LocalDateTime.now());

        paymentDao.save(payment);
        return payment;
    }

//    @Override
//    public void handlePaymentSuccess(String txnId, String gateway) {
//        Payment payment = paymentDao.findByTxnId(txnId);
//        if (payment != null && "INITIATED".equals(payment.getStatus())) {
//            payment.setStatus("SUCCESS");
//            paymentDao.saveOrUpdate(payment);
//            bookingService.confirmBooking(payment.getBooking().getId());
//        }
//    }

//    @Override
//    public void handlePaymentFailure(String txnId, String gateway) {
//        Payment payment = paymentDao.findByTxnId(txnId);
//        if (payment != null) {
//            payment.setStatus("FAILED");
//            paymentDao.saveOrUpdate(payment);
//        }
//    }
    
    /*
     *to Handle Email Notifications on success full payment 
     * 
     */


    @Override
    public void handlePaymentSuccess(String txnId, String gateway) {
        Payment payment = paymentDao.findByTxnId(txnId);
        if (payment != null && "INITIATED".equals(payment.getStatus())) {
            payment.setStatus("SUCCESS");
            paymentDao.saveOrUpdate(payment);

            // Confirm booking
            Booking booking = payment.getBooking();
            bookingService.confirmBooking(booking.getId());

            // Send confirmation email
            emailService.sendBookingConfirmation(payment.getUser(), booking);
        }
    }

    @Override
    public void handlePaymentFailure(String txnId, String gateway) {
        Payment payment = paymentDao.findByTxnId(txnId);
        if (payment != null) {
            payment.setStatus("FAILED");
            paymentDao.saveOrUpdate(payment);

            // Send failure email
            emailService.sendPaymentFailure(payment.getUser(), payment.getBooking());
        }
    }
    

    public void handleBookingCancellation(Booking booking) {
        Payment payment = paymentDao.findByBookingId(booking.getId());
        if (payment != null && "SUCCESS".equals(payment.getStatus())) {
            refundService.initiateRefund(payment, booking, booking.getUser(), payment.getAmount());
            // Optionally call Razorpay refund API and then mark as SUCCESS
        }
    }
}




//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.sat.tmf.movietkt.dao.PaymentDao;
//import com.sat.tmf.movietkt.entities.Booking;
//import com.sat.tmf.movietkt.entities.Payment;
//import com.sat.tmf.movietkt.entities.User;
//import com.sat.tmf.movietkt.service.BookingService;
//import com.sat.tmf.movietkt.service.PaymentService;
//
//@Service
//@Transactional
//public class PaymentServiceImpl implements PaymentService {
//
//    @Autowired
//    private PaymentDao paymentDao;
//
//    @Autowired
//    private BookingService bookingService;
//
//    @Override
//    public Payment initiatePayment(Booking booking, User user, String gateway) {
//        // Create a payment record
//        Payment payment = new Payment();
//        payment.setBooking(booking);
//        payment.setUser(user);
//        payment.setAmount(booking.getAmount());
//        payment.setStatus("INITIATED");
//        payment.setGateway(gateway);
//        payment.setCreatedAt(LocalDateTime.now());
//
//        // Create Razorpay order and store transaction ID
//        if ("RAZORPAY".equalsIgnoreCase(gateway)) {
//            String razorpayOrderId = createRazorpayOrder(booking);
//            payment.setTxnId(razorpayOrderId);
//        }
//
//        paymentDao.save(payment);
//        return payment;
//    }
//
//    /**
//     * Creates an order on Razorpay and returns the Razorpay Order ID.
//     */
//    private String createRazorpayOrder(Booking booking) {
//        try {
//            // Initialize Razorpay client with your test/live keys
//            RazorpayClient client = new RazorpayClient("rzp_test_yourKeyHere", "rzp_test_yourSecretHere");
//
//            // Build Razorpay order request
//            JSONObject orderRequest = new JSONObject();
//            orderRequest.put("amount", booking.getAmount().multiply(new BigDecimal(100))); // convert to paise
//            orderRequest.put("currency", "INR");
//            orderRequest.put("receipt", "order_rcptid_" + booking.getId());
//
//            // Create order on Razorpay
//            Order order = client.Orders.create(orderRequest);
//
//            // Return the order ID to store as txnId
//            return order.get("id");
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create Razorpay order", e);
//        }
//    }
//
//    @Override
//    public void handlePaymentSuccess(String txnId, String gateway) {
//        Payment payment = paymentDao.findByTxnId(txnId);
//        if (payment != null && "INITIATED".equals(payment.getStatus())) {
//            payment.setStatus("SUCCESS");
//            paymentDao.saveOrUpdate(payment);
//            bookingService.confirmBooking(payment.getBooking().getId());
//        }
//    }
//
//    @Override
//    public void handlePaymentFailure(String txnId, String gateway) {
//        Payment payment = paymentDao.findByTxnId(txnId);
//        if (payment != null) {
//            payment.setStatus("FAILED");
//            paymentDao.saveOrUpdate(payment);
//        }
//    }
//}


