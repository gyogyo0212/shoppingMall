package com.example.shoppingmall.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.domain.order.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
