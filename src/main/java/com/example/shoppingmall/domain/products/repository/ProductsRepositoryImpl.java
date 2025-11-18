package com.example.shoppingmall.domain.products.repository;

import java.util.List;

import com.example.shoppingmall.domain.products.entity.Products;
import com.example.shoppingmall.domain.products.entity.QProducts;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {

	private final JPAQueryFactory queryFactory; // 여기로 교체

	@Override
	public List<Products> search(String keyword, String category) {
		QProducts p = QProducts.products;

		return queryFactory
			.selectFrom(p)
			.where(
				keywordContains(p, keyword),
				categoryEq(p, category)
			)
			.fetch();
	}

	private BooleanExpression keywordContains(QProducts p, String keyword) {
		if (keyword == null || keyword.isBlank())
			return null;

		String cleanedKeyword = keyword.replaceAll("\\s+", "").toLowerCase();

		// Querydsl에서 SQL 함수 REPLACE를 호출
		return Expressions.stringTemplate(
			"function('REPLACE', {0}, ' ', '')", p.name
		).lower().like("%" + cleanedKeyword + "%");
	}

	private BooleanExpression categoryEq(QProducts p, String category) {
		return (category == null || category.isBlank()) ? null : p.category.lower().eq(category.toLowerCase());
	}
}
