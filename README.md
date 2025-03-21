# 온라인 서점
온라인 서점을 위한 웹 애플리케이션을 개발.

## 개요
상점 주인이 책을 검색하고, 상세 정보를 보고 편집하며, 각 책의 판매 수량을 확인할 수 있어야 한다.

## 기술 스택
### Backend
- Java 17
- Spring Boot 3.4.1
### Frontend
- Vue 3.5.13
- Vuetify 3.7.18
- Vite 5.4.14

## Build
### Frontend
```shell
pnpm run build
```
### Backend
```shell
./gradlew bootWar
```

## Run
### Frontend
```shell
pnpm install && pnpm run dev
```
### Backend
`run BookstoreApplication.class`

## Docs
`
/swagger-ui/index.html
`

## API 명세

### 1. 책 추가
- **Endpoint**: `POST /api/books`
- **Request Body**:
```json5
{
  "title": "책 제목",
  "author": "저자",
  "price": 10000,
  "cnt": 1
}
```
- **Response**:
```json5
{
  "statusCode": 200,
  "dt": "2025-01-01T00:00:00.000+09:00",
  "bid": 1
}
```

### 2-1. 책 상세 정보 조회
- **Endpoint**: `GET /api/books/:id`

- **Response**:
```json5
{
  "bid": 1,
  "title": "책 제목",
  "author": "저자",
  "price": 10000,
  "cnt": 1
}
```

### 2-2. 책 목록 조회
- **Endpoint**: `GET /api/books`
- **Request Param**:

|name| type   | value      |
|---|--------|------------|
|keyword| String | 책 제목 또는 저자 |
|page|Integer | 0          |
|size|Integer | 10         |
|sort|String | bid,desc   |
- 
- **Response**:
```json5
{
  "content": [
    {
      "bid": 1,
      "title": "책 제목",
      "author": "저자",
      "price": 10000,
      "cnt": 1
    }
  ],
  "page": {
    "size": 10,
    "number": 0,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

### 3. 책 정보 수정
- **Endpoint**: `PUT /api/books/:id`
- **Request Body**:
```json5
{
  "title": "책 제목",
  "author": "저자",
  "price": 10000,
  "cnt": 10
}
```
- **Response**:
```json5
{
  "statusCode": 200,
  "dt": "2025-01-01T00:00:00.000+09:00"
}
```

### 4. 책 삭제
- **Endpoint**: `DELETE /api/books/:id`
- **Response**:
```json5
{
  "statusCode": 200,
  "dt": "2025-01-01T00:00:00.000+09:00"
}
```
