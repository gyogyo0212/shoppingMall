package com.example.shoppingmall.domain.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.domain.order.dto.request.OrderItemDto;
import com.example.shoppingmall.domain.order.dto.request.OrdersCreatRequestDto;
import com.example.shoppingmall.domain.order.dto.response.OrdersListResponseDto;
import com.example.shoppingmall.domain.order.entity.OrderItems;
import com.example.shoppingmall.domain.order.entity.Orders;
import com.example.shoppingmall.domain.order.entity.OrdersStatus;
import com.example.shoppingmall.domain.order.repository.OrdersRepository;
import com.example.shoppingmall.domain.order.dto.response.OrdersCreatResponseDto;
import com.example.shoppingmall.domain.products.entity.Products;
import com.example.shoppingmall.domain.products.repository.ProductsRepository;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.global.exception.CustomException;
import com.example.shoppingmall.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersService {

	private final OrdersRepository ordersRepository;
	private final ProductsRepository productsRepository;

	@Transactional
	public OrdersCreatResponseDto creatOrders(OrdersCreatRequestDto requestDto, User user){

		if (requestDto.getItems() == null || requestDto.getItems().isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_ORDER_ITEMS);
		}

		// 주문 엔티티 생성
		Orders order = Orders.builder()
							 .user(user)
							 .shippingAddress(requestDto.getShippingAddress())
							 .build();

		List<OrderItemDto> items = requestDto.getItems();

		for(int i = 0; i < items.size(); i++){
			OrderItemDto item = items.get(i);

			Long productsId = item.getId();
			Long quantity = item.getQuantity();

			Products products = productsRepository.findById(productsId)
				.orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

			if(products.getStock()< quantity){
				throw new CustomException(ErrorCode.OUT_OF_STOCK);
			}
			OrderItems orderItem = OrderItems.builder()
											 .orders(order)
											 .products(products)
											 .quantity(item.getQuantity())
											 .status(OrdersStatus.PENDING) // OrderItems에서 상태 관리
											 .build();

			order.addOrderItem(orderItem);

			// 재고 차감
			products.setStock(products.getStock() - quantity);
			productsRepository.save(products);
		}

		// 주문 저장 (OrderItems도 Cascade.ALL로 같이 저장)
		ordersRepository.save(order);

		// 응답 DTO 반환
		return new OrdersCreatResponseDto(order.getId(), order.getOrderItems().size());

	}

	// 주문목록 조회
	@Transactional
	public OrdersListResponseDto ordersList (User user){
		if (user == null) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
		List<Orders> orders = ordersRepository.findAllByUser(user);

		if(orders.isEmpty()){
			throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
		}
		List<OrdersCreatResponseDto> ordersList = orders.stream()
			.map(order ->new OrdersCreatResponseDto(
				order.getId(),
				order.getOrderItems().size()
			))
			.toList();

		return new OrdersListResponseDto(ordersList);
	}

}
