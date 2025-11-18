package com.example.shoppingmall.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.domain.cart.entity.Cart;
import com.example.shoppingmall.domain.products.entity.Products;
import com.example.shoppingmall.domain.user.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserAndProducts(User user, Products products);
}
