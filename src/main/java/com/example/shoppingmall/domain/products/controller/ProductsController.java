package com.example.shoppingmall.domain.products.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.domain.products.dto.response.ProductsSearchResponseDto;
import com.example.shoppingmall.global.dto.ApiResponseDto;
import com.example.shoppingmall.domain.products.dto.requset.ProductsCreatedRequestDto;
import com.example.shoppingmall.domain.products.dto.requset.ProductsUpdateRequestDto;
import com.example.shoppingmall.domain.products.dto.response.ProductsInfoResponseDto;
import com.example.shoppingmall.domain.products.dto.response.ProductsListResponseDto;
import com.example.shoppingmall.domain.products.service.ProductsService;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductsController {

	private final ProductsService productsService;

	// 상품 등록
	@PostMapping("/admin/products")
	public ResponseEntity<ApiResponseDto<Void>> creatProducts(
		@RequestBody @Valid ProductsCreatedRequestDto requestDto) {
		productsService.creatProducts(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(null));
	}


	// 상품 검색
	@GetMapping("/products/search")
	public ResponseEntity<ApiResponseDto<List<ProductsSearchResponseDto>>> searchProducts(
		@RequestParam(required = false) String keyword,
		@RequestParam(required = false) String category,
		@RequestParam(required = false) Long minPrice,
		@RequestParam(required = false) Long maxPrice,
		@RequestParam(required = false,defaultValue = "latest") String sort) {
		List<ProductsSearchResponseDto> result = productsService.searchProducts(keyword,category,minPrice,maxPrice,sort);

		String message = "총 " + result.size() + "건 검색됨";

		return ResponseEntity.ok(ApiResponseDto.success(result,message));
	}


	// 상품 수정
	@PatchMapping("/admin/products/{id}")
	public ResponseEntity<ApiResponseDto<Void>> updateProducts(@PathVariable long id,
		@RequestBody ProductsUpdateRequestDto requestDto) {
		productsService.updateProducts(id,requestDto);
		return ResponseEntity.ok(ApiResponseDto.success(null));
	}


	// 상품 목록 조회
	@GetMapping("/products")
	public ResponseEntity<ApiResponseDto<ProductsListResponseDto>> getProductsList() {
		ProductsListResponseDto products = productsService.getProductsList();

		return ResponseEntity.ok(ApiResponseDto.success(products));
	}


	// 상품 상세 조회
	@GetMapping("/products/{id}")
	public ResponseEntity<ApiResponseDto<ProductsInfoResponseDto>> productsInfo(@PathVariable  long id){
		ProductsInfoResponseDto productsInfo = productsService.productsInfo(id);
		return ResponseEntity.ok(ApiResponseDto.success(productsInfo));
	}

}
