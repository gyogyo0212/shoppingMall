package com.example.shoppingmall.domain.payment.entity;

import java.time.LocalDateTime;

import org.springframework.data.domain.Sort;

import com.example.shoppingmall.domain.order.entity.Orders;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "orders_id",nullable = false)
	private Orders orders;

	@Column(nullable = false)
	private Long amount;

	@Column(name = "payment_method",nullable = false)
	private PaymentMethod paymentMethod;

	@Column(name = "payment_status",nullable = false)
	private PaymentStatus paymentStatus;

	@Column(name = "payment_date",nullable = false)
	private LocalDateTime paymentDate;

	@Builder
	public Payment(
		Orders orders,
		Long amount,
		PaymentMethod paymentMethod,
		PaymentStatus paymentStatus,
		LocalDateTime paymentDate) {
		this.orders = orders;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}
}
