# 1. Node.js 환경에서 프론트엔드 빌드
FROM node:22 AS frontend-builder

WORKDIR /app/frontend
COPY frontend/ ./
RUN corepack enable && pnpm install && pnpm run build

# 2. Java 환경에서 백엔드 빌드
FROM amazoncorretto:17 AS backend-builder

WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew clean build -x test

# 3. 최종 실행 환경 (JDK 런타임만 포함)
FROM amazoncorretto:17

WORKDIR /app
COPY --from=backend-builder /app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
