package com.example.shoppingmall.global.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI customOpenAPI() {

		String jwtSchemeName = "bearerAuth";

		SecurityScheme securityScheme = new SecurityScheme()
			.name(jwtSchemeName)
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER);

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList(jwtSchemeName);

		return new OpenAPI()
			.info(new Info()
					  .title("ShoppingMall API")
					  .version("1.0")
					  .description("API 문서")
			)
			.addSecurityItem(securityRequirement)
			.components(new Components().addSecuritySchemes(jwtSchemeName, securityScheme));
	}
}
