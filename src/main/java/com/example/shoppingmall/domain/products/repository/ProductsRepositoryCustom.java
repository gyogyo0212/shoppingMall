package com.example.shoppingmall.domain.products.repository;

import java.util.List;

import com.example.shoppingmall.domain.products.entity.Products;

public interface ProductsRepositoryCustom {
	List<Products> search(String keyword,String category);
}
