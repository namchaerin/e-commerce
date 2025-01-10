package kr.hhplus.be.server.common.exceptions;

public class InvalidCouponException extends RuntimeException {

    public InvalidCouponException() {
        super("사용할 수 없는 쿠폰입니다.");
    }

    public InvalidCouponException(String message) {
        super(message);
    }

}
