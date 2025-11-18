package com.example.shoppingmall.domain.cart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.domain.cart.dto.CartRequestDto;
import com.example.shoppingmall.domain.cart.entity.Cart;
import com.example.shoppingmall.domain.cart.repository.CartRepository;
import com.example.shoppingmall.domain.products.entity.Products;
import com.example.shoppingmall.domain.products.repository.ProductsRepository;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.repository.UserRepository;
import com.example.shoppingmall.global.exception.CustomException;
import com.example.shoppingmall.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final ProductsRepository productsRepository;

	// 장바구니 상품 등록
	@Transactional
	public void addToCart(CartRequestDto requestDto,long userId){

		User user = userRepository.findById(userId)
								  .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
		System.out.println("requestDto.getProductsId() = " + requestDto.getProductId());

		Products products = productsRepository.findById(requestDto.getProductId())
											  .orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
		System.out.println(products); // null이면 DB 문제
		Cart cart = cartRepository.findByUserAndProducts(user, products);

		if(cart != null){
			cart.addQuantity(requestDto.getQuantity());
		}else {
			cart = Cart.builder()
					   .user(user)
					   .products(products)
					   .quantity(requestDto.getQuantity())
					   .build();
		}
		cartRepository.save(cart);
	}
}
