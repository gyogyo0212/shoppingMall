package com.example.shoppingmall.domain.payment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.shoppingmall.domain.order.entity.Orders;
import com.example.shoppingmall.domain.order.entity.OrdersStatus;
import com.example.shoppingmall.domain.order.repository.OrdersRepository;
import com.example.shoppingmall.domain.payment.dto.request.PaymentProcessRequestDto;
import com.example.shoppingmall.domain.payment.dto.response.PaymentProcessResponseDto;
import com.example.shoppingmall.domain.payment.entity.Payment;
import com.example.shoppingmall.domain.payment.entity.PaymentStatus;
import com.example.shoppingmall.domain.payment.repository.PaymentRepository;
import com.example.shoppingmall.domain.products.entity.Products;
import com.example.shoppingmall.domain.products.repository.ProductsRepository;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.repository.UserRepository;
import com.example.shoppingmall.global.exception.CustomException;
import com.example.shoppingmall.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;
	private final ProductsRepository productsRepository;
	private final OrdersRepository ordersRepository;

	public PaymentProcessResponseDto paymentProcess(Long orderId, PaymentProcessRequestDto requestDto, User user) {

		// 사용자 검증
		User dbUser = userRepository.findById(user.getId())
									.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		// 주문 조회
		Orders orders = ordersRepository.findById(orderId)
										.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		//총 금액
		long totalPrice = orders.getOrderItems().stream()
								.mapToLong(item -> item.getProducts().getPrice()* item.getQuantity())
								.sum();

		// 금액 검증
		if (!requestDto.getAmount().equals(totalPrice)) {
			throw new CustomException(ErrorCode.INVALID_PAYMENT_AMOUNT);
		}

		orders.getOrderItems().forEach(item -> {
			Products product = item.getProducts();
			long remaining = product.getStock() - item.getQuantity();
			if (remaining < 0) {
				throw new CustomException(ErrorCode.OUT_OF_STOCK);
			}
			product.setStock(remaining);
			productsRepository.save(product);
		});
		// 결제 상태 변경
		orders.getOrderItems().forEach(item -> item.setStatus(OrdersStatus.COMPLETED));

		ordersRepository.save(orders);


		// 9) 결제 기록 저장(선택 사항)
		Payment payment = Payment.builder()
								 .orders(orders)
								 .amount(totalPrice)
								 .paymentMethod(requestDto.getPaymentMethod())
								 .paymentStatus(PaymentStatus.SUCCESS)
								 .paymentDate(LocalDateTime.now())
								 .build();
		paymentRepository.save(payment);

		String paymentId = "PAY-" + orderId; // 실제 발급된 paymentId로 교체

		return new PaymentProcessResponseDto(paymentId);

	}
}
