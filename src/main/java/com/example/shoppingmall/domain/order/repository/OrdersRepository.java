package com.example.shoppingmall.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.domain.order.entity.Orders;
import com.example.shoppingmall.domain.user.entity.User;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	List<Orders> findAllByUser(User user);
}
