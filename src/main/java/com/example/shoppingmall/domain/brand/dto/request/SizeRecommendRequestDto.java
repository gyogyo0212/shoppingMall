package com.example.shoppingmall.domain.brand.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SizeRecommendRequestDto {

    @NotBlank
    private String brandName;
    private int height;
    private int chest;
    private int waist;
}
