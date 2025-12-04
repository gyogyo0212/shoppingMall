package com.example.shoppingmall.domain.products.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.domain.products.dto.requset.ProductsCreatedRequestDto;
import com.example.shoppingmall.domain.products.dto.requset.ProductsUpdateRequestDto;
import com.example.shoppingmall.domain.products.dto.response.ProductsInfoResponseDto;
import com.example.shoppingmall.domain.products.dto.response.ProductsListResponseDto;
import com.example.shoppingmall.domain.products.dto.response.ProductsResponseDto;
import com.example.shoppingmall.domain.products.dto.response.ProductsSearchResponseDto;
import com.example.shoppingmall.domain.products.entity.Products;
import com.example.shoppingmall.domain.products.repository.ProductsRepository;
import com.example.shoppingmall.global.exception.CustomException;
import com.example.shoppingmall.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsService {

	private final ProductsRepository productsRepository;

	// 상품 등록
	public void creatProducts(ProductsCreatedRequestDto requestDto) {
		if (productsRepository.existsByName(requestDto.getName())) {
			throw new CustomException(ErrorCode.PRODUCT_EXISTS);
		}
		Products products = Products.builder()
									.name(requestDto.getName())
									.price(requestDto.getPrice())
									.stock(requestDto.getStock())
									.category(requestDto.getCategory())
									.build();

		productsRepository.save(products);

	}

	//상품 수정
	@Transactional
	public void updateProducts(long id, ProductsUpdateRequestDto updateRequestDto) {
		Products products = productsRepository.findById(id)
											  .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

		products.update(
			updateRequestDto.getPrice(),
			updateRequestDto.getStock()
		);
	}

	// 상품 목록 조회

	public ProductsListResponseDto getProductsList() {
		List<Products> productsList = productsRepository.findAll();
		List<ProductsResponseDto> productsDto = productsList.stream()
															.map(p -> new ProductsResponseDto(p.getId(), p.getName(),
																							  p.getPrice(),
																							  p.getStock()))
															.toList();

		return new ProductsListResponseDto(productsDto);
	}

	//상품 상세 조회
	public ProductsInfoResponseDto productsInfo(long id) {
		Products products = productsRepository.findById(id)
											  .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

		return new ProductsInfoResponseDto(products);

	}

	// 상품 검색
	public List<ProductsSearchResponseDto> searchProducts(String keyword, String category,Long minPrice, Long maxPrice, String sort) {
		//
		List<Products> products = productsRepository.search(keyword, category,minPrice,maxPrice);

		//정렬 처리
		switch (sort) {
			case "price_asc":
				products.sort(Comparator.comparingLong(Products::getPrice));
				break;
			case "price_desc":
				products.sort(Comparator.comparingLong(Products::getPrice).reversed());
				break;
			case "Latest":
			default:
				products.sort(Comparator.comparing(Products::getCreatedAt).reversed());
		}
		return products.stream()
					   .map(product -> new ProductsSearchResponseDto(
						   product.getId(),
						   product.getName(),
						   product.getPrice(),
						   product.getCategory()
					   ))
					   .toList();
	}
}


