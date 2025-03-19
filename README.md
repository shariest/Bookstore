# 온라인 서점
온라인 서점을 위한 웹 애플리케이션을 개발.

## 개요
상점 주인이 책을 검색하고, 상세 정보를 보고 편집하며, 각 책의 판매 수량을 확인할 수 있어야 한다.

## 기술 스택
- Java 17
- Spring Boot 3.4.1

## Build
```shell
./gradlew bootWar
```

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
  "id": 1
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
```json5
{
  "keyword": "제목",
  "page": 0,
  "size": 10,
  "sortBy": "제목,desc"
}
```

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

### 3. 책 정보 수정
- **Endpoint**: `PUT /api/books/:id`
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
  "id": 1
}
```

### 4. 책 삭제
- **Endpoint**: `DELETE /api/books/:id`
- **Response**:
```json5
{
  "id": 1
}
```