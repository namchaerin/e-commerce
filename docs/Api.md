
---

# e-커머스 API 명세서

## 설명
이 API는 **상품 주문 시스템**을 위한 API로, 사용자는 여러 상품을 선택하고 잔액을 이용하여 결제를 진행할 수 있습니다. 또한, 판매량이 높은 상품을 추천하고, 사용자 잔액 관리 및 쿠폰 발급 기능도 제공합니다.

### API 명세서 구조
- **상품 관련 API**
    - 상품 목록 조회
    - 상위 상품 조회
    - 상품 상세 조회
- **잔액 관련 API**
    - 잔액 조회
    - 잔액 충전
- **쿠폰 관련 API**
    - 선착순 쿠폰 발급
    - 보유 쿠폰 목록 조회
- **주문 및 결제 관련 API**
    - 주문 및 결제

---

## 1. 상품 관련 API

### 1.1 상품 목록 조회

- **요청**
    - **Method**: `GET`
    - **URL**: `/api/v1/products`

- **설명**: 등록된 모든 상품을 조회합니다.

- **응답**
    - **Status**: `200 OK`
    - **Body**:
      ```json
      [
        {
          "productId": 1,
          "productName": "상품1",
          "price": 10000,
          "stock": 10
        },
        {
          "productId": 2,
          "productName": "상품2",
          "price": 15000,
          "stock": 40
        },
        {
          "productId": 3,
          "productName": "상품3",
          "price": 25000,
          "stock": 20
        }
      ]
      ```

---

### 1.2 상위 상품 조회

- **요청**
    - **Method**: `GET`
    - **URL**: `/api/v1/products/top?days=3`
    - **쿼리 파라미터**:
        - `days`: 조회할 판매량 기준 기간 (예: 3일)

- **설명**: 최근 `days` 일 내에 판매량 상위 상품을 조회합니다.

- **응답**
    - **Status**: `200 OK`
    - **Body**:
      ```json
      [
        {
          "rank": 1,
          "productId": 1,
          "productName": "상품1",
          "price": 10000,
          "stock": 10
        },
        {
          "rank": 2,
          "productId": 2,
          "productName": "상품2",
          "price": 15000,
          "stock": 40
        },
        {
          "rank": 3,
          "productId": 3,
          "productName": "상품3",
          "price": 25000,
          "stock": 20
        }
      ]
      ```

---

### 1.3 상품 조회

- **요청**
    - **Method**: `GET`
    - **URL**: `/api/v1/products/{productId}`

- **설명**: 특정 상품에 대한 정보를 조회합니다.

- **응답**
    - **Status**: `200 OK`
    - **Body**:
      ```json
      {
        "productId": 3,
        "productName": "상품3",
        "price": 25000,
        "stock": 20
      }
      ```

---

## 2. 잔액 관련 API

### 2.1 잔액 조회

- **요청**
    - **Method**: `GET`
    - **URL**: `/api/v1/balance?userId=1`

- **설명**: 사용자별 포인트 잔액을 조회합니다.

- **응답**
    - **Status**: `200 OK`
    - **Body**:
      ```json
      {
        "userId": 1,
        "balance": 50000
      }
      ```

---

### 2.2 잔액 충전

- **요청**
    - **Method**: `POST`
    - **URL**: `/api/v1/balance`
    - **Body**:
      ```json
      {
        "userId": 1,
        "amount": 10000
      }
      ```

- **설명**: 사용자의 포인트를 충전합니다.

- **응답**
    - **Status**: `200 OK`
    - **Body**:
      ```json
      {
        "userId": 1,
        "balance": 60000
      }
      ```

---

## 3. 쿠폰 관련 API

### 3.1 선착순 쿠폰 발급

- **요청**
    - **Method**: `POST`
    - **URL**: `/api/v1/coupons`
    - **Body**:
      ```json
      {
        "userId": 1,
        "couponId": 1
      }
      ```

- **설명**: 선착순 쿠폰을 발급합니다.

- **응답**
    - **Status**: `200 OK`
    - **Body**:
      ```json
      {
        "success": true,
        "couponId": 1,
        "message": "쿠폰 발급 성공"
      }
      ```

---

### 3.2 보유 쿠폰 목록 조회

- **요청**
    - **Method**: `GET`
    - **URL**: `/api/v1/coupons?userId=1`

- **설명**: 사용자가 보유한 쿠폰 목록을 조회합니다.

- **응답**
    - **Status**: `200 OK`
    - **Body**:
      ```json
      [
        {
          "couponId": 1,
          "discountRate": 10,
          "expirationDate": "2025-02-01"
        },
        {
          "couponId": 2,
          "discountRate": 5,
          "expirationDate": "2025-02-17"
        },
        {
          "couponId": 3,
          "discountRate": 7,
          "expirationDate": "2025-03-01"
        }
      ]
      ```

---

## 4. 주문/결제 관련 API

### 4.1 주문 및 결제

- **요청**
    - **Method**: `POST`
    - **URL**: `/api/v1/orders`
    - **Body**:
      ```json
      {
        "userId": 1,
        "items": [
          {
            "productId": 1,
            "quantity": 3
          }
        ],
        "couponId": 1
      }
      ```

- **설명**: 상품을 주문하고 결제합니다.

- **응답**
    - **Status**: `200 OK`
    - **Body**:
      ```json
      {
        "success": true,
        "orderId": 1,
        "newBalance": 30000,
        "message": "주문 및 결제 성공"
      }
      ```

---