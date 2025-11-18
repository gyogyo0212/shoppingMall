package com.example.shoppingmall.domain.order.entity;

public enum OrdersStatus {
	PENDING,   // 결제 또는 처리 대기 중
	COMPLETED, // 주문 완료
	CANCELLED,  // 주문 취소됨
	FAILED // 주문 실패
}
