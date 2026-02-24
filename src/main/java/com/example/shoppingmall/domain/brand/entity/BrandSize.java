package com.example.shoppingmall.domain.brand.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrandSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sizeName;

    @Column(nullable = false)
    private int heightMin;

    @Column(nullable = false)
    private int heightMax;

    @Column(nullable = false)
    private int chestMin;

    @Column(nullable = false)
    private int chestMax;

    @Column(nullable = false)
    private int waistMin;

    @Column(nullable = false)
    private int waistMax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Builder
    public BrandSize(String sizeName, int heightMin, int heightMax,
                     int chestMin, int chestMax, int waistMin, int waistMax, Brand brand) {
        this.sizeName = sizeName;
        this.heightMin = heightMin;
        this.heightMax = heightMax;
        this.chestMin = chestMin;
        this.chestMax = chestMax;
        this.waistMin = waistMin;
        this.waistMax = waistMax;
        this.brand = brand;
    }

    public boolean fits(int height, int chest, int waist) {
        return heightMin <= height && height <= heightMax
                && chestMin <= chest && chest <= chestMax
                && waistMin <= waist && waist <= waistMax;
    }
}
