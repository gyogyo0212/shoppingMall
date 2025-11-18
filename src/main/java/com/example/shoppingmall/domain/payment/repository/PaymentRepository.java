package com.example.shoppingmall.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
