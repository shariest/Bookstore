# Step 1: Frontend 빌드 (Node 22)
FROM node:22 AS frontend-builder

WORKDIR /app
COPY frontend/package.json frontend/pnpm-lock.yaml ./
RUN corepack enable && corepack prepare pnpm@latest --activate && pnpm install --frozen-lockfile

COPY frontend ./
RUN pnpm run build

# Step 2: Backend 빌드 (Gradle + Amazon Corretto 17)
FROM amazoncorretto:17 AS backend-builder

WORKDIR /app
COPY . .

# 프론트엔드 빌드 결과를 백엔드 resources/static 폴더로 이동
COPY --from=frontend-builder ../src/main/resources/static /app/src/main/resources/static

RUN chmod +x gradlew && ./gradlew build -x test

# Step 3: 최종 실행 (Amazon Corretto 17)
FROM amazoncorretto:17

WORKDIR /app
COPY --from=backend-builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]