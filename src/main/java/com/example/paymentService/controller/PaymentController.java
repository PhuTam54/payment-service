package com.example.paymentService.controller;

import com.example.paymentService.entity.Payment;
import com.example.paymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/create_payment")
    public String creatPayment( @RequestParam(name = "orderId") Long orderId) throws UnsupportedEncodingException {
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/api/v1/payment";
        String baseUrl = "http://localhost:8080";
        String vnpayUrl = paymentService.creatPayment( baseUrl, orderId);
        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", vnpayUrl);
        paymentService.savePayment(orderId);
        return vnpayUrl;
    }

    @GetMapping("/vnPayReturn/{orderId}")
    public ResponseEntity<String> handleVnPayReturn(@RequestParam(name = "vnp_ResponseCode") String responseCode,
                                                    @PathVariable Long orderId
                                                    ) {
            // Xử lý phản hồi từ VNPAY

            if ("00".equals(responseCode)) {
                // Giao dịch thành công
                paymentService.updateStatusPayment(true, orderId);
                paymentService.UpdateStatusOrder(true, orderId);

                return ResponseEntity.ok("Giao dịch thành công");
            } else {
                // Giao dịch không thành công
                paymentService.updateStatusPayment(false,orderId);
                paymentService.UpdateStatusOrder(false,orderId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giao dịch không thành công");
            }
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<Page<Payment>> getByUsername(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "5") int limit,
                                                       @PathVariable Long userId){
        return ResponseEntity.ok(paymentService.getByUsername(PageRequest.of(page-1, limit),userId));
    }
     @GetMapping("/{id}")
        public ResponseEntity<Payment> getById(@PathVariable Long id){
            return ResponseEntity.ok(paymentService.getById(id));
        }
    @GetMapping("/order/{id}")
    public ResponseEntity<Payment> getByOrderId(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getByOrderId(id));
    }

}


