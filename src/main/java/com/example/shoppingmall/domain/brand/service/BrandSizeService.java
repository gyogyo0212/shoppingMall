package com.example.shoppingmall.domain.brand.service;

import com.example.shoppingmall.domain.brand.dto.request.BrandCreateRequestDto;
import com.example.shoppingmall.domain.brand.dto.request.SizeRecommendRequestDto;
import com.example.shoppingmall.domain.brand.dto.response.SizeRecommendResponseDto;
import com.example.shoppingmall.domain.brand.entity.Brand;
import com.example.shoppingmall.domain.brand.entity.BrandSize;
import com.example.shoppingmall.domain.brand.repository.BrandRepository;
import com.example.shoppingmall.global.exception.CustomException;
import com.example.shoppingmall.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandSizeService {

    private final BrandRepository brandRepository;

    @Transactional
    public void createBrand(BrandCreateRequestDto requestDto) {
        if (brandRepository.existsByName(requestDto.getName())) {
            throw new CustomException(ErrorCode.BRAND_ALREADY_EXISTS);
        }

        Brand brand = Brand.builder()
                .name(requestDto.getName())
                .build();

        requestDto.getSizes().forEach(sizeDto -> {
            BrandSize size = BrandSize.builder()
                    .sizeName(sizeDto.getSizeName())
                    .heightMin(sizeDto.getHeightMin())
                    .heightMax(sizeDto.getHeightMax())
                    .chestMin(sizeDto.getChestMin())
                    .chestMax(sizeDto.getChestMax())
                    .waistMin(sizeDto.getWaistMin())
                    .waistMax(sizeDto.getWaistMax())
                    .brand(brand)
                    .build();
            brand.getSizes().add(size);
        });

        brandRepository.save(brand);
    }

    public SizeRecommendResponseDto recommend(SizeRecommendRequestDto requestDto) {
        Optional<Brand> brandOpt = brandRepository.findByName(requestDto.getBrandName());

        if (brandOpt.isEmpty()) {
            return SizeRecommendResponseDto.builder()
                    .brandName(requestDto.getBrandName())
                    .result("UNKNOWN")
                    .build();
        }

        Brand brand = brandOpt.get();
        List<BrandSize> sizes = brand.getSizes();
        int height = requestDto.getHeight();
        int chest = requestDto.getChest();
        int waist = requestDto.getWaist();

        // 입력 순서 기준 첫 번째 맞는 사이즈
        Optional<BrandSize> fitting = sizes.stream()
                .filter(s -> s.fits(height, chest, waist))
                .findFirst();

        if (fitting.isPresent()) {
            return SizeRecommendResponseDto.builder()
                    .brandName(requestDto.getBrandName())
                    .result(fitting.get().getSizeName())
                    .build();
        }

        return SizeRecommendResponseDto.builder()
                .brandName(requestDto.getBrandName())
                .result(classifyMismatch(sizes, height, chest, waist))
                .build();
    }

    private String classifyMismatch(List<BrandSize> sizes, int height, int chest, int waist) {
        int maxH = sizes.stream().mapToInt(BrandSize::getHeightMax).max().orElse(0);
        int maxC = sizes.stream().mapToInt(BrandSize::getChestMax).max().orElse(0);
        int maxW = sizes.stream().mapToInt(BrandSize::getWaistMax).max().orElse(0);
        int minH = sizes.stream().mapToInt(BrandSize::getHeightMin).min().orElse(Integer.MAX_VALUE);
        int minC = sizes.stream().mapToInt(BrandSize::getChestMin).min().orElse(Integer.MAX_VALUE);
        int minW = sizes.stream().mapToInt(BrandSize::getWaistMin).min().orElse(Integer.MAX_VALUE);

        if (height > maxH && chest > maxC && waist > maxW) {
            return "UP";
        }
        if (height < minH && chest < minC && waist < minW) {
            return "DOWN";
        }
        return "MISMATCH";
    }
}
