package com.example.shoppingmall.domain.payment.dto.request;

import com.example.shoppingmall.domain.payment.entity.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentProcessRequestDto {

	private final PaymentMethod paymentMethod;

	private final Long amount;

}
