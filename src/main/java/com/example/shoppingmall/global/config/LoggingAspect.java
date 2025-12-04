package com.example.shoppingmall.global.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	private final ObjectMapper objectMapper = new ObjectMapper();


	@Around("within(@org.springframework.web.bind.annotation.RestController *)")
	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

		long startTime = System.currentTimeMillis();

		// HTTP Request 정보
		HttpServletRequest request =
			((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		String api = request.getMethod() + " " + request.getRequestURI();
		String requestBody = objectMapper.writeValueAsString(joinPoint.getArgs());

		log.info("==============================================");
		log.info("API: {}", api);
		log.info("REQUEST: {}", requestBody);

		try {
			Object result = joinPoint.proceed(); // 실제 메소드 실행

			String responseBody = objectMapper.writeValueAsString(result);
			long duration = System.currentTimeMillis() - startTime;

			log.info("RESPONSE: {}", responseBody);
			log.info("SUCCESS: true");
			log.info("DURATION: {}ms", duration);
			log.info("==============================================");

			return result;
		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			log.error("ERROR: {}", e.getMessage());
			log.info("SUCCESS: false");
			log.info("DURATION: {}ms", duration);
			log.info("==============================================");

			throw e;
		}
	}
}
