package com.example.shoppingmall.domain.brand.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BrandCreateRequestDto {

    @NotBlank
    private String name;

    @Valid
    @NotEmpty
    private List<BrandSizeRequestDto> sizes;
}
