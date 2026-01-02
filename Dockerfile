# 1. 실행 환경
FROM eclipse-temurin:17-jdk

# 2. 작업 디렉토리
WORKDIR /app

# 3. 빌드된 jar 복사
COPY build/libs/*.jar app.jar

# 4. 포트 오픈
EXPOSE 8080

# 5. 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]
