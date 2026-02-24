package com.example.shoppingmall.domain.brand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SizeRecommendResponseDto {

    private String brandName;
    private String result;
}
