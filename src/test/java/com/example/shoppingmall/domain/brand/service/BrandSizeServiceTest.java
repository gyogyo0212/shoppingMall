package com.example.shoppingmall.domain.brand.service;

import com.example.shoppingmall.domain.brand.dto.request.BrandCreateRequestDto;
import com.example.shoppingmall.domain.brand.dto.request.BrandSizeRequestDto;
import com.example.shoppingmall.domain.brand.dto.request.SizeRecommendRequestDto;
import com.example.shoppingmall.domain.brand.dto.response.SizeRecommendResponseDto;
import com.example.shoppingmall.domain.brand.entity.Brand;
import com.example.shoppingmall.domain.brand.entity.BrandSize;
import com.example.shoppingmall.domain.brand.repository.BrandRepository;
import com.example.shoppingmall.global.exception.CustomException;
import com.example.shoppingmall.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BrandSizeServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandSizeService brandSizeService;

    // ── 헬퍼 ──────────────────────────────────────────────────

    private Brand buildBrand(String name, List<BrandSize> sizes) {
        Brand brand = Brand.builder().name(name).build();
        brand.getSizes().addAll(sizes);
        return brand;
    }

    private BrandSize size(String sizeName,
                           int hMin, int hMax,
                           int cMin, int cMax,
                           int wMin, int wMax) {
        return BrandSize.builder()
                .sizeName(sizeName)
                .heightMin(hMin).heightMax(hMax)
                .chestMin(cMin).chestMax(cMax)
                .waistMin(wMin).waistMax(wMax)
                .build();
    }

    // ── recommend ─────────────────────────────────────────────

    @Test
    @DisplayName("브랜드가 존재하지 않으면 UNKNOWN 반환")
    void recommend_brandNotFound_returnsUnknown() {
        given(brandRepository.findByName("NoName")).willReturn(Optional.empty());

        SizeRecommendResponseDto result =
                brandSizeService.recommend(new SizeRecommendRequestDto("NoName", 170, 90, 70));

        assertThat(result.getBrandName()).isEqualTo("NoName");
        assertThat(result.getResult()).isEqualTo("UNKNOWN");
    }

    @Test
    @DisplayName("치수가 딱 맞는 사이즈가 있으면 해당 사이즈명 반환")
    void recommend_exactFit_returnsSizeName() {
        Brand brand = buildBrand("Nike", List.of(
                size("S", 160, 170, 80, 90, 60, 70),
                size("M", 170, 180, 90, 100, 70, 80)
        ));
        given(brandRepository.findByName("Nike")).willReturn(Optional.of(brand));

        SizeRecommendResponseDto result =
                brandSizeService.recommend(new SizeRecommendRequestDto("Nike", 175, 95, 75));

        assertThat(result.getResult()).isEqualTo("M");
    }

    @Test
    @DisplayName("여러 사이즈가 맞을 때 입력 순서 기준 첫 번째 사이즈 반환")
    void recommend_multipleFit_returnsFirstByOrder() {
        // S(160-170)와 M(165-175)이 겹치는 구간 → height=167이면 S가 먼저
        Brand brand = buildBrand("Zara", List.of(
                size("S", 160, 170, 80, 90, 60, 70),
                size("M", 165, 175, 85, 95, 65, 75)
        ));
        given(brandRepository.findByName("Zara")).willReturn(Optional.of(brand));

        SizeRecommendResponseDto result =
                brandSizeService.recommend(new SizeRecommendRequestDto("Zara", 167, 87, 67));

        assertThat(result.getResult()).isEqualTo("S");
    }

    @Test
    @DisplayName("모든 사이즈 최대치보다 크면 UP 반환")
    void recommend_allMeasurementsAboveMax_returnsUp() {
        Brand brand = buildBrand("Zara", List.of(
                size("S", 155, 165, 75, 85, 55, 65),
                size("M", 165, 175, 85, 95, 65, 75)
        ));
        given(brandRepository.findByName("Zara")).willReturn(Optional.of(brand));

        // maxH=175, maxC=95, maxW=75 → 모두 초과
        SizeRecommendResponseDto result =
                brandSizeService.recommend(new SizeRecommendRequestDto("Zara", 190, 110, 90));

        assertThat(result.getResult()).isEqualTo("UP");
    }

    @Test
    @DisplayName("모든 사이즈 최소치보다 작으면 DOWN 반환")
    void recommend_allMeasurementsBelowMin_returnsDown() {
        Brand brand = buildBrand("Zara", List.of(
                size("S", 155, 165, 75, 85, 55, 65),
                size("M", 165, 175, 85, 95, 65, 75)
        ));
        given(brandRepository.findByName("Zara")).willReturn(Optional.of(brand));

        // minH=155, minC=75, minW=55 → 모두 미달
        SizeRecommendResponseDto result =
                brandSizeService.recommend(new SizeRecommendRequestDto("Zara", 140, 60, 40));

        assertThat(result.getResult()).isEqualTo("DOWN");
    }

    @Test
    @DisplayName("일부 치수만 범위를 벗어나면 MISMATCH 반환")
    void recommend_partialMismatch_returnsMismatch() {
        Brand brand = buildBrand("Zara", List.of(
                size("S", 155, 165, 75, 85, 55, 65),
                size("M", 165, 175, 85, 95, 65, 75)
        ));
        given(brandRepository.findByName("Zara")).willReturn(Optional.of(brand));

        // height=172(M 범위 내), chest=100(M 최대 95 초과), waist=70(M 범위 내) → 부분 불일치
        SizeRecommendResponseDto result =
                brandSizeService.recommend(new SizeRecommendRequestDto("Zara", 172, 100, 70));

        assertThat(result.getResult()).isEqualTo("MISMATCH");
    }

    // ── createBrand ───────────────────────────────────────────

    @Test
    @DisplayName("새 브랜드 등록 성공")
    void createBrand_newBrand_success() {
        given(brandRepository.existsByName("Nike")).willReturn(false);

        BrandCreateRequestDto request = new BrandCreateRequestDto(
                "Nike",
                List.of(new BrandSizeRequestDto("M", 165, 175, 85, 95, 65, 75))
        );

        assertThatNoException().isThrownBy(() -> brandSizeService.createBrand(request));
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    @DisplayName("중복 브랜드 등록 시 BRAND_ALREADY_EXISTS 예외 발생")
    void createBrand_duplicateName_throwsException() {
        given(brandRepository.existsByName("Nike")).willReturn(true);

        BrandCreateRequestDto request = new BrandCreateRequestDto(
                "Nike",
                List.of(new BrandSizeRequestDto("M", 165, 175, 85, 95, 65, 75))
        );

        assertThatThrownBy(() -> brandSizeService.createBrand(request))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.BRAND_ALREADY_EXISTS.getMessage());
    }
}
