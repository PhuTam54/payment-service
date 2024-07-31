package com.example.paymentService.repository;

import com.example.paymentService.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findByUserId(Pageable pageable,Long i);
    Payment findByOrderId(Long orderId);
}
