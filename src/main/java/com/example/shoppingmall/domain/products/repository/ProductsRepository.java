package com.example.shoppingmall.domain.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.domain.products.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {
	boolean existsByName(String name);

}
