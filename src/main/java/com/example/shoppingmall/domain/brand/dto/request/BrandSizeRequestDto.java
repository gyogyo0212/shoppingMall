package com.example.shoppingmall.domain.brand.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandSizeRequestDto {

    @NotBlank
    private String sizeName;
    private int heightMin;
    private int heightMax;
    private int chestMin;
    private int chestMax;
    private int waistMin;
    private int waistMax;
}
