package com.example.shoppingmall.domain.brand.controller;

import com.example.shoppingmall.domain.brand.dto.request.BrandCreateRequestDto;
import com.example.shoppingmall.domain.brand.dto.request.SizeRecommendRequestDto;
import com.example.shoppingmall.domain.brand.dto.response.SizeRecommendResponseDto;
import com.example.shoppingmall.domain.brand.service.BrandSizeService;
import com.example.shoppingmall.global.dto.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BrandController {

    private final BrandSizeService brandSizeService;

    @PostMapping("/admin/brands")
    public ResponseEntity<ApiResponseDto<Void>> createBrand(
            @RequestBody @Valid BrandCreateRequestDto requestDto) {
        brandSizeService.createBrand(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(null));
    }

    @PostMapping("/brands/recommend")
    public ResponseEntity<ApiResponseDto<SizeRecommendResponseDto>> recommend(
            @RequestBody @Valid SizeRecommendRequestDto requestDto) {
        SizeRecommendResponseDto result = brandSizeService.recommend(requestDto);
        return ResponseEntity.ok(ApiResponseDto.success(result));
    }
}
