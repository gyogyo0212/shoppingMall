package com.example.shoppingmall.domain.products.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsListResponseDto {
	private List<ProductsResponseDto> products;
}
