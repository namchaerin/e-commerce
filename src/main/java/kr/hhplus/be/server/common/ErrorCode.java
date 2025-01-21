package kr.hhplus.be.server.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 사용자 관련 오류
    USER_NOT_FOUND(1000, "User not found.", HttpStatus.NOT_FOUND),
    USER_NOT_AUTHORIZED(1001, "User not authorized.", HttpStatus.UNAUTHORIZED),
    USER_SESSION_EXPIRED(1002, "User session expired.", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS(1003, "Invalid credentials provided.", HttpStatus.UNAUTHORIZED),

    // 주문 관련 오류
    ORDER_NOT_FOUND(2000, "Order not found.", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_PROCESSED(2001, "Order already processed.", HttpStatus.CONFLICT),
    ORDER_PAYMENT_FAILED(2002, "Order payment failed.", HttpStatus.BAD_REQUEST),
    ORDER_CANCEL_FAILED(2003, "Order cancellation failed.", HttpStatus.BAD_REQUEST),
    ORDER_ITEM_NOT_AVAILABLE(2004, "Order item not available.", HttpStatus.NOT_FOUND),

    // 결제 관련 오류
    PAYMENT_FAILED(4000, "Payment failed.", HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_NOT_SUPPORTED(4001, "Payment method not supported.", HttpStatus.BAD_REQUEST),
    PAYMENT_GATEWAY_ERROR(4002, "Payment gateway error.", HttpStatus.SERVICE_UNAVAILABLE),
    INSUFFICIENT_FUNDS(4003, "Insufficient funds.", HttpStatus.BAD_REQUEST),
    PAYMENT_TIMEOUT(4004, "Payment timeout.", HttpStatus.REQUEST_TIMEOUT),

    // 쿠폰 관련 오류
    COUPON_NOT_FOUND(6000, "Coupon not found.", HttpStatus.NOT_FOUND),
    COUPON_EXPIRED(6001, "Coupon has expired.", HttpStatus.BAD_REQUEST),
    COUPON_ALREADY_USED(6002, "Coupon has already been used.", HttpStatus.BAD_REQUEST),
    COUPON_NOT_APPLICABLE(6003, "Coupon is not applicable for this order.", HttpStatus.BAD_REQUEST),
    COUPON_LIMIT_EXCEEDED(6004, "Coupon usage limit exceeded.", HttpStatus.BAD_REQUEST),
    COUPON_EXHAUSTED(6005, "Coupon has been exhausted.", HttpStatus.BAD_REQUEST), // 소진된 쿠폰 에러
    COUPON_NOT_OWNED(6006, "User does not own the coupon.", HttpStatus.BAD_REQUEST),  // 유저가 보유하지 않은 쿠폰

    // 상품 관련 오류
    PRODUCT_NOT_FOUND(7000, "Product not found.", HttpStatus.NOT_FOUND),  // 상품이 존재하지 않음
    PRODUCT_OUT_OF_STOCK(7001, "Product is out of stock.", HttpStatus.BAD_REQUEST),  // 상품이 품절됨
    PRODUCT_PRICE_INVALID(7002, "Product price is invalid.", HttpStatus.BAD_REQUEST),  // 상품 가격 오류
    PRODUCT_NOT_AVAILABLE(7003, "Product not available for this order.", HttpStatus.BAD_REQUEST),  // 상품이 해당 주문에 적용 불가

    // 기타 서버 오류
    INTERNAL_SERVER_ERROR(9000, "Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(9001, "Service unavailable.", HttpStatus.SERVICE_UNAVAILABLE);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(Integer code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
