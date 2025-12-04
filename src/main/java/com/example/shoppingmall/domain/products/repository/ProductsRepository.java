package com.example.shoppingmall.domain.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shoppingmall.domain.products.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {
	boolean existsByName(String name);


	@Query("SELECT p FROM Products p " +
		"WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
		"AND (:category IS NULL OR LOWER(p.category) = LOWER(:category))" +
		"AND (:minPrice IS NULL OR p.price >= :minPrice) " +
		"AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
	List<Products> search(
		@Param("keyword") String keyword,
		@Param("category") String category,
		@Param("minPrice") Long minPrice,
		@Param("maxPrice") Long maxPrice);

}
